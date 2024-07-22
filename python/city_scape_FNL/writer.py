import user_input as input

import turtle
import time


# was too lazy to do the shrinking text thing
def screenwrite(t, text, font_size, pos, sleep_time, erase):
    # write text for certain amount of time
    t.teleport(pos[0], pos[1] - (font_size / 2))
    t.write(
        text,
        align="center",
        font=("Times New Roman", font_size, "normal"),
    )
    turtle.update()
    time.sleep(sleep_time)
    if erase:
        t.undo()


def write_introduction():
    # introduction
    readiness_turtle = turtle.Turtle()
    readiness_turtle.hideturtle()

    screenwrite(readiness_turtle, "Coming to you this fall...", 36, [0, 0], 2, True)
    screenwrite(readiness_turtle, "in around 8 seconds...", 36, [0, 0], 2, True)
    screenwrite(
        readiness_turtle, "the greatest hit of the century...", 36, [0, 0], 2, True
    )
    screenwrite(readiness_turtle, "Space Race", 64, [0, 0], 4, True)
    screenwrite(
        readiness_turtle, "Your mind is about to be blown. ", 24, [0, 100], 0, False
    )

    # user readiness inquiry
    user_ready = input.get_text_affirmation(
        "Are you? ", "Are you ready to have your mind blown? "
    )
    readiness_turtle.undo()
    if user_ready:
        screenwrite(readiness_turtle, "Good. ", 32, [0, 0], 2, True)
    else:
        readiness_turtle.color("red")
        screenwrite(readiness_turtle, "you will pay. ", 32, [0, 0], 5, True)


def show_countdown(direction):
    countdown_turtle = turtle.Turtle()
    countdown_turtle.hideturtle()
    countdown_turtle.color("white")
    screenwrite(countdown_turtle, "3", 48, [-400 * direction, -100], 1, True)
    screenwrite(countdown_turtle, "2", 48, [-400 * direction, -100], 1, True)
    screenwrite(countdown_turtle, "1", 48, [-400 * direction, -100], 1, True)
