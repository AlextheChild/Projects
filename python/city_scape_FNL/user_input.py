import turtle
import os
import time

# import speech_recognition as sr

global affirmations
affirmations = ["yes", "yeah", "ye", "ya", "yesh", "yerp", "yep"]


def get_positive_integer(window_title, question):
    user_has_given_integer_greater_than_zero = False
    while not user_has_given_integer_greater_than_zero:
        try:
            number = int(turtle.textinput(window_title, question))
        except ValueError:
            continue
        if number > 0:
            user_has_given_integer_greater_than_zero = True
    return number


def get_text_affirmation(window_title, question):
    input = turtle.textinput(window_title, question)

    if input in affirmations:
        return True
    else:
        return False


def get_announcement_consent():
    os.system(
        'say "Would you like to continue being told the area of each building\'s facade?"'
    )
    time.sleep(2)

    # ! I literally couldn't test this because my pip can't install pyaudio
    # try:
    # microphone setup
    # recognizer = sr.Recognizer()
    # recognizer.adjust_for_ambient_noise(sr.Microphone(), duration=0.2)

    # audio = recognizer.listen(sr.Microphone())  # listens for one word or phrase
    # user_input = recognizer.recognize_google(audio)  # speech recognition

    # if user_input.lower() in affirmations:
    #     os.system(
    #         'say "This endeavor will be sysiphisian for the both of us, but ok. "'
    #     )
    #     return True
    # else:
    #     os.system('say "Thank you for freeing the both of us. "')
    #     return False
    # except:
    os.system(
        'say "There was a problem getting your response. We will take the liberty of assuming that you said "no". "'
    )
    return False
