import numpy as np
from PIL import Image
from tensorflow import keras

# get image file
image = Image.open('../Images/Canvas.png').convert('L')
image_for_ai = image.crop((125, 0, 725, 599)).resize((28, 28))
image_for_ai.save('Image.png')

# grayscaled pixel array
colored_pixels = np.asarray(image_for_ai)
colored_pixels = colored_pixels.astype('float32')
colored_pixels /= 255
colored_pixels = np.expand_dims(colored_pixels, axis=0)

# predict
model = keras.models.load_model('../model.h5')
exp_array = np.ndarray.tolist(model.predict(colored_pixels, verbose=0))
prediction = []

for i in range(len(exp_array[0])):
    exp_array[0][i] = f'{exp_array[0][i]:.20f}'
    prediction.append(exp_array[0][i])
for i in range(len(prediction)):
    print(str(i) + ": " + str(prediction[i]))
