from selenium import webdriver
import pyautogui as robot
import numpy as np

robot.PAUSE = 0
robot.FAILSAFE = False

# ! selenium has 1440x900, pyautogui has 1920x1200


def open_window():
    global driver

    driver = webdriver.Firefox(webdriver.firefox.options.Options())
    driver.set_window_size(720, 900)
    driver.set_window_position(0, 0)
    driver.get("https://www.google.com/search?q=bts")

    # click balloon
    button_position = np.array([70, 280])  # 1440x900
    button_position = button_position / np.array([1440, 900]) * np.array([1920, 1200])
    robot.moveTo(x=button_position[0], y=button_position[1])
    robot.click()


def close_window():
    driver.close()
