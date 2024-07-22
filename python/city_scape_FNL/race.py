import writer

import turtle
from PIL import Image
import random
import os


def register_car_shapes():
    turtle.register_shape("images/racer_images/space_beckwith_slide.gif")
    turtle.register_shape("images/racer_images/kachow_left.gif")
    turtle.register_shape("images/racer_images/kachow_right.gif")


def setup():
    # create turtles
    global beckwith
    beckwith = turtle.Turtle()
    beckwith.shape("images/racer_images/space_beckwith_slide.gif")
    beckwith.teleport(-350, -225 + 57)
    beckwith.penup()
    beckwith.color("white")

    global mcqueen
    mcqueen = turtle.Turtle()
    mcqueen.shape("images/racer_images/kachow_left.gif")
    mcqueen.teleport(-350, -275 + 27 -10)
    mcqueen.penup()
    mcqueen.color("white")


def race():
    direction = -1
    while True:
        # countdown
        writer.show_countdown(direction)

        # race
        while -350 <= beckwith.xcor() <= 350 and -350 <= mcqueen.xcor() <= 350:
            beckwith.fd(random.randint(1, 10))
            # writer.screenwrite(
            #     beckwith, str(beckwith.xcor()), 12, [beckwith.xcor() + 6, beckwith.ycor() + 6], 0, True
            # )
            mcqueen.fd(random.randint(1, 10))
            # writer.screenwrite(
            #     mcqueen, str(mcqueen.xcor()), 12, [mcqueen.xcor() + 6, mcqueen.ycor() + 6], 0, True
            # )
            turtle.update()

        # winner announcement/dance
        if abs(beckwith.xcor()) > abs(mcqueen.xcor()):
            os.system('say "Mr. Beckwith wins!"')
            for i in range(10):
                beckwith.setheading(90)
                beckwith.fd(10)
                turtle.update()
                beckwith.setheading(270)
                beckwith.fd(10)
                turtle.update()
        else:
            os.system('say "Lightning McQueen wins!"')
            for i in range(10):
                mcqueen.setheading(90)
                mcqueen.fd(10)
                turtle.update()
                mcqueen.setheading(270)
                mcqueen.fd(10)
                turtle.update()

        # change direction
        direction *= -1
        beckwith.teleport(350 * direction, -225 + 57)
        mcqueen.teleport(350 * direction, -275 + 27 -10)
        beckwith.setheading(90 + 90 * direction)
        mcqueen.setheading(90 + 90 * direction)
        if direction == -1:
            mcqueen.shape("images/racer_images/kachow_left.gif")
        else:
            mcqueen.shape("images/racer_images/kachow_right.gif")
        turtle.update()
