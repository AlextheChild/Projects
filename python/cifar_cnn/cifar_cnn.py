import pygame
import pygame.camera
import tkinter.filedialog

# import ctypes

from PIL import Image
import numpy as np

import tensorflow as tf
from tensorflow import keras

tf.function(reduce_retracing=True)


def select_file():
    file_name = tkinter.filedialog.askopenfilename()
    return file_name


def get_rescaled_image(image):
    if image.get_width() / image.get_height() < (canvas_width - 100) / (
        canvas_height - 100
    ):
        new_height = canvas_height - 102
        ratio = image.get_height() / new_height
        return pygame.transform.scale(image, ((image.get_width() / ratio), new_height))
    else:
        new_width = canvas_width - 102
        ratio = image.get_width() / new_width
        return pygame.transform.scale(image, (new_width, image.get_height() / ratio))


def draw_image(image):
    image_rect = image.get_rect()
    image_rect.center = canvas_width / 2, canvas_height / 2
    canvas.blit(image, image_rect.topleft)
    # border
    pygame.draw.rect(
        canvas,
        (0, 0, 0),
        ((image_rect.left, image_rect.top), (image_rect.w, image_rect.h)),
        1,
    )


def prep_image(file):
    im = Image.open(file)

    # get rid of opacity
    im = im.convert("RGB")

    # resize
    im = im.resize((32, 32))

    im.save("images/ai_input_image.png")

    # convert to array
    pixels = np.asarray(im)[None, :]
    pixels = pixels.astype("float32")
    pixels /= 255

    return pixels


def get_prediction(pixels):
    return model.predict(pixels, verbose=0)


def get_max_label(probabilities):
    if (
        probabilities[0]
        == probabilities[1]
        == probabilities[2]
        == probabilities[3]
        == probabilities[4]
        == probabilities[5]
        == probabilities[6]
        == probabilities[7]
        == probabilities[8]
        == probabilities[9]
    ):
        return "uncertain"

    max_value = 0
    max_index = 0
    for i in range(len(probabilities)):
        if probabilities[i] > max_value:
            max_value = probabilities[i]
            max_index = i

    return labels[max_index]


def get_ai_input_image():
    return pygame.image.load("images/ai_input_image.png")


def bar_graph(probabilities):
    line_margin = 40
    guess_margin = 60

    line_x = canvas_width + line_margin - 1
    pygame.draw.line(
        canvas,
        (0, 0, 0),
        (line_x, margin + guess_margin),
        (line_x, canvas_height - margin),
    )
    line_height = (canvas_height - 2 * margin) - guess_margin

    for i in range(10):
        # bars
        bar_length = 150 * probabilities[i]
        bar_height = (line_height) / 13
        bar_left = canvas_width + line_margin
        bar_top = margin + guess_margin + i * bar_height * 4 / 3

        if i < 9:
            pygame.draw.rect(
                canvas, (0, 0, 0), ((bar_left, bar_top), (bar_length, bar_height))
            )
        # fraction correction
        else:
            pygame.draw.rect(
                canvas, (0, 0, 0), ((bar_left, bar_top + 2), (bar_length, bar_height))
            )

        # percentages
        percentage = str(int(round(probabilities[i], 2) * 100)) + "%"
        render_text(
            14,
            False,
            percentage,
            (0, 0, 0),
            "right",
            (line_x - 2, bar_top + bar_height * 4 / 6),
        )

        # labels
        render_text(
            14, False, labels[i], (0, 0, 0), "center", (canvas_width + 100, bar_top - 1)
        )


def render_text(size, bolded, text, color, side, pos):
    font = pygame.font.SysFont("Times New Roman", size, bolded)
    text = font.render(text, True, color)
    textRect = text.get_rect()
    if side == "center":
        textRect.midbottom = pos
    elif side == "right":
        textRect.bottomright = pos

    canvas.blit(text, textRect.topleft)


def error():
    render_text(
        18, True, "Error getting file. ", (255, 0, 0), "center", (canvas_width / 2, 100)
    )
    pygame.draw.rect(
        canvas,
        (0, 0, 0),
        (50, 50, canvas_width - margin * 2, canvas_height - margin * 2),
        1,
    )


# window management
pygame.init()
# ctypes.windll.shcore.SetProcessDpiAwareness(True)
icon = pygame.Surface((1, 1))
icon.fill((255, 255, 255))
pygame.display.set_icon(icon)
pygame.display.set_caption("Image")
display_screen = pygame.display.Info()
window_width, window_height = (
    display_screen.current_w * 11 / 14,
    display_screen.current_h * 6 / 7,
)
canvas = pygame.display.set_mode((window_width, window_height), pygame.RESIZABLE)
canvas_width, canvas_height = (window_width - 200, window_height)
margin = 50

# blank image
white = pygame.Surface((canvas_width - 2 * margin, canvas_height - 2 * margin))
white.fill((255, 255, 255))
pygame.image.save(white, "images/white.png")
file = "images/white.png"
image = pygame.image.load(file)
ai_input_image = None

# ai setup
model = keras.models.load_model("cnn/cifar_cnn.h5")
labels = [
    "airplane",
    "automobile",
    "bird",
    "cat",
    "deer",
    "dog",
    "frog",
    "horse",
    "ship",
    "truck",
]
probabilities = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

# camera
pygame.camera.init()
cameras = pygame.camera.list_cameras()
camera = pygame.camera.Camera(cameras[0])
camera_on = False
c_presses = 0

clock = pygame.time.Clock()
running = True
while running:
    clock.tick(100)

    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

        if event.type == pygame.VIDEORESIZE:
            if canvas.get_size()[0] > 302 and canvas.get_size()[1] > 102:
                window_width, window_height = canvas.get_size()
                canvas_width, canvas_height = (window_width - 200, window_height)

        if event.type == pygame.DROPFILE:
            file = event.file
            try:
                image = pygame.image.load(file)
            except pygame.error:
                image = None
            except FileNotFoundError:
                image = None

        if event.type == pygame.MOUSEBUTTONDOWN:
            # select new image
            if event.button == pygame.BUTTON_LEFT:
                mouseX, mouseY = pygame.mouse.get_pos()
                if (
                    mouseX >= margin
                    and mouseX <= canvas_width - margin
                    and mouseY >= margin
                    and mouseY <= canvas_height - margin
                ):
                    tkinter.filedialog.askopenfilename()
                    file = select_file()
                    try:
                        image = pygame.image.load(file)
                    except pygame.error:
                        image = None
                    except FileNotFoundError:
                        image = None

            # send to ai
            if event.button == pygame.BUTTON_RIGHT:
                if camera_on:
                    c_presses += 1
                    image = pygame.transform.flip(camera.get_image(), True, False)
                    pygame.image.save(image, "camera.png")
                    file = "camera.png"
                    camera_on = False
                    camera.stop()

                try:
                    probabilities = (get_prediction(prep_image(file))[0]).tolist()
                    ai_input_image = get_ai_input_image()
                except OSError:
                    probabilities = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                    ai_input_image = None
                except AttributeError:
                    probabilities = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                    ai_input_image = None

        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_c:
                c_presses += 1
                if c_presses % 2 == 1:
                    camera.start()
                    camera_on = True
                else:
                    camera_on = False
                    camera.stop()
                    try:
                        image = pygame.image.load(file)
                    except pygame.error:
                        image = None
                    except FileNotFoundError:
                        image = None

    # ---------------BorderLayout.CENTER---------------#
    canvas.fill((255, 255, 255))
    pygame.draw.line(canvas, (0, 0, 0), (0, 0), (window_width, 0))
    render_text(
        14, False, "Choose a file:", (0, 0, 0), "center", (canvas_width / 2, 40)
    )

    # show camera
    if camera_on:
        image = pygame.transform.flip(camera.get_image(), True, False)

    # show image
    try:
        draw_image(get_rescaled_image(image))
    except AttributeError:
        error()

    # ---------------BorderLayout.EAST---------------#
    canvas.fill((200, 200, 200), (canvas_width, 1, 200, window_height))
    pygame.draw.line(
        canvas, (0, 0, 0), (canvas_width, 0), (canvas_width, window_height)
    )

    # ai guess
    render_text(
        14,
        True,
        str(get_max_label(probabilities)),
        (0, 0, 0),
        "center",
        (canvas_width + 100, 40),
    )

    # ai input image
    try:
        canvas.blit(ai_input_image, (canvas_width + 10, 50))
    except TypeError:
        pass
    except AttributeError:
        pass

    # bar graph
    bar_graph(probabilities)
    pygame.display.flip()


pygame.quit()
