import os

os.environ["TF_CPP_MIN_LOG_LEVEL"] = "3"

import website as w
import display as d

"""
! grey color for padding
! cv2 color masking
! there are a lot of magic variables/numbers in the gui code so maybe change that
! make framerate better
! opencv for image processing
! convert all saved imagest from RGBA to RGB
! global variables 
! take all data, mask out the backgrounds with random noise, train on that dataset
"""


def main():
    # w.open_window()
    d.display_ai_output()
    # w.close_window()
    os.remove("screen.png")


main()
