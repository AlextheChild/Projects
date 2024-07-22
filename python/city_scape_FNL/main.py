__author__ = "Alexandre Hus-Shao"

# special features:
# random thickness for each building's outline
# includes the name of the city
# one of the turtles is a car
# a background image behind the city
# each building has a different width
# says something that depends on how big building facade is
# turtles turtle around when they reach either end
# winning turtle does a little dance at the end


import writer
import user_input as input
import bob_services as bob
import race

import turtle
from PIL import Image


def setup():
    # set background image
    stars = Image.open("images/background_image/stars.gif")
    stars = stars.resize((900, 600))
    stars.save("images/background_image/stars.gif")
    turtle.register_shape("images/background_image/stars.gif")
    turtle.shape("images/background_image/stars.gif")

    # intro
    writer.write_introduction()

    # get user inputs
    global num_buildings
    num_buildings = input.get_positive_integer(
        "How many? ", "How many buildings would you like built? "
    )

    global first_address
    first_address = input.get_positive_integer(
        "What is it? ", "What should the address of the first building be? "
    )


def draw_city():
    bob.lay_road()
    # bob.construct_dome()
    bob.assemble_billboard()
    bob.build_buildings(num_buildings)
    bob.install_doors(num_buildings)
    bob.write_addresses(num_buildings, first_address)


def race_cars():
    race.register_car_shapes()
    race.setup()
    race.race()


def main():
    screen = turtle.Screen()
    screen.setup(900, 600)
    screen.title("Mind blower")

    running = True
    while running:
        screen.clear()
        screen.colormode(255)
        screen.tracer(0)

        # setup
        setup()

        # drawing
        draw_city()

        # ! loops forever
        # racing
        race_cars()

        # continuation inquiry
        running = input.get_text_affirmation(
            "Do you? ", "Do you want to view this amazing creation again? "
        )

    screen.exitonclick()


main()
