import tensorflow as tf
from tensorflow import keras
import cv2
import numpy as np
from PIL import Image

tf.function(reduce_retracing=True)

model = keras.models.load_model("models/cnn/saved_models/vgg_good_20_20.keras")


def get_predictions():
    return get_selective_search_proposals()


# ————— ccn functions ————— #


def prepare_cnn_image():
    image_resolution = 20

    # edit image
    image = Image.open("screen.png")

    image = image.convert("RGB").resize(
        [
            int(image.width * (2880 / 1920) // (image_resolution)),
            int(image.height * (1800 / 1200) // (image_resolution)),
        ]
    )
    image = np.asarray(image)[None, :]
    image = image.astype("float32") / 255

    # add padding
    shape = image.shape
    image = image.tolist()
    padded_image = np.zeros(
        [1, 1800 // image_resolution, 2880 // image_resolution, 3]
    ).tolist()
    for i in range(shape[1]):
        for j in range(shape[2]):
            padded_image[0][i][j] = image[0][i][j]
    padded_image = np.asarray(padded_image)

    return image


def get_cnn_prediction():
    balloon_positions = model.predict(prepare_cnn_image(), verbose=0)

    # scale the predictions back up to screen size
    for i in balloon_positions:
        i[0] *= 1920
        i[1] *= 1200
        i[2] *= 1920
        i[3] *= 1200

    return balloon_positions


# ————— yolo functions ————— #

"""
! get selective search boxes
! for the ones that are too big, maybe split them in half widthwise
! classify them
! apply NMS
"""


def get_selective_search_proposals():
    global total, count

    selective_search = cv2.ximgproc.segmentation.createSelectiveSearchSegmentation()
    selective_search.setBaseImage(prepare_cv2_image())
    selective_search.switchToSelectiveSearchFast()

    rects = selective_search.process()

    rects = [
        rects[0] * 20 * (1920 / 2880),
        rects[1] * 20 * (1200 / 1800),
        rects[2] * 20 * (1920 / 2880),
        rects[3] * 20 * (1200 / 1800),
    ]

    proposals = []

    proposal_list = []
    for rect in rects:
        x = rect[0]
        y = rect[1]
        w = rect[2]
        h = rect[3]

        # size filter
        if w < 200 or h < 200 or w > 900 or h > 900:
            continue

        # split in halves
        if w > 500 and h > 600:
            tl = [x, y, w / 2, h / 2]
            bl = [x, y + h / 2, w / 2, h / 2]
            tr = [x + w / 2, y, w / 2, h / 2]
            br = [x + w / 2, y + h / 2, w / 2, h / 2]

            proposal_list.extend([tl, bl, tr, br])
        elif w > 500:
            l = [x, y, w / 2, h]
            r = [x + w / 2, y, w / 2, h]

            proposal_list.extend([l, r])
        elif h > 600:
            t = [x, y, w, h / 2]
            b = [x, y + h / 2, w, h / 2]

            proposal_list.extend([t, b])
        else:
            proposal_list.append(rect)

        proposals.extend(proposal_list)

    return proposals


def prepare_cv2_image():
    image_resolution = 20  #! bullshit

    # edit image
    image = Image.open("screen.png")

    image = image.convert("RGB").resize(
        [
            int(image.width * (2880 / 1920) // (image_resolution)),
            int(image.height * (1800 / 1200) // (image_resolution)),
        ]
    )
    image = np.asarray(image)

    return image


# ————— substitute prediction functions ————— #


"""
# only works on stop signs
def get_haar_prediction():
    img = cv2.imread("outputs/screen.png")
    img_gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

    # give cv2 data and get predictions
    balloon_data = cv2.CascadeClassifier("stop_data.xml")
    balloon_bounds = balloon_data.detectMultiScale(img_gray, minSize=(100, 100))

    balloon_positions = []
    for x, y, width, height in balloon_bounds:
        balloon_positions.append([x, y, x + width, y + height])

    return balloon_positions


# is stupid
def get_random_prediction(l, d):
    x = random.randint(l[0], l[0] + d[0])
    y = random.randint((900 - l[1]) // 2, l[1] + d[1] - 50)
    return [[x, y, x, y]]
"""
