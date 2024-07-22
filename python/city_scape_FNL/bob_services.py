import user_input as input
import writer

import turtle
import random
import os


def lay_road():
    # turtle setup
    bob_the_road_builder = turtle.Turtle()
    bob_the_road_builder.speed(0)
    bob_the_road_builder.color((50, 50, 50), (50, 50, 50))

    # lay road
    bob_the_road_builder.teleport(-450, -200)
    bob_the_road_builder.begin_fill()
    bob_the_road_builder.pendown()
    bob_the_road_builder.fd(900)
    bob_the_road_builder.right(90)
    bob_the_road_builder.fd(100)
    bob_the_road_builder.right(90)
    bob_the_road_builder.fd(900)
    bob_the_road_builder.right(90)
    bob_the_road_builder.fd(100)
    bob_the_road_builder.right(90)
    bob_the_road_builder.end_fill()

    # finish lines
    bob_the_road_builder.color("blue")
    bob_the_road_builder.width(2)
    bob_the_road_builder.pendown()
    bob_the_road_builder.teleport(-350, -200)
    bob_the_road_builder.setheading(270)
    bob_the_road_builder.fd(100)
    bob_the_road_builder.teleport(350, -200)
    bob_the_road_builder.setheading(270)
    bob_the_road_builder.fd(100)

    # paint road lines
    bob_the_road_builder.color("white")
    bob_the_road_builder.width(1)
    bob_the_road_builder.teleport(-450, -250 - 1)
    bob_the_road_builder.setheading(0)
    while bob_the_road_builder.xcor() < 450:
        bob_the_road_builder.pendown()
        bob_the_road_builder.fd(20)

    bob_the_road_builder.teleport(-450, -250 + 1)
    bob_the_road_builder.setheading(0)
    while bob_the_road_builder.xcor() < 450:
        bob_the_road_builder.pendown()
        bob_the_road_builder.fd(20)

    bob_the_road_builder.hideturtle()


def construct_dome():
    # sad that transparency doesn't work for turtles
    bob_the_dome_constructor = turtle.Turtle()
    bob_the_dome_constructor.color("white")
    bob_the_dome_constructor.width(2)
    bob_the_dome_constructor.teleport(400, -200)
    bob_the_dome_constructor.setheading(90)
    bob_the_dome_constructor.circle(400, 180)
    bob_the_dome_constructor.hideturtle()


def assemble_billboard():
    bob_the_billboard_assembler = turtle.Turtle()
    bob_the_billboard_assembler.color("black", (64, 33, 16))
    bob_the_billboard_assembler.width(3)

    # legs
    bob_the_billboard_assembler.teleport(-200, -200)
    bob_the_billboard_assembler.begin_fill()
    bob_the_billboard_assembler.setheading(90)
    bob_the_billboard_assembler.fd(325)
    bob_the_billboard_assembler.left(90)
    bob_the_billboard_assembler.fd(10)
    bob_the_billboard_assembler.left(90)
    bob_the_billboard_assembler.fd(325)
    bob_the_billboard_assembler.end_fill()
    turtle.update()

    bob_the_billboard_assembler.teleport(200, -200)
    bob_the_billboard_assembler.begin_fill()
    bob_the_billboard_assembler.setheading(90)
    bob_the_billboard_assembler.fd(325)
    bob_the_billboard_assembler.right(90)
    bob_the_billboard_assembler.fd(10)
    bob_the_billboard_assembler.right(90)
    bob_the_billboard_assembler.fd(325)
    bob_the_billboard_assembler.end_fill()
    turtle.update()

    # body
    bob_the_billboard_assembler.teleport(-250, 250)
    bob_the_billboard_assembler.begin_fill()
    bob_the_billboard_assembler.setheading(0)
    bob_the_billboard_assembler.fd(500)
    bob_the_billboard_assembler.right(90)
    bob_the_billboard_assembler.fd(125)
    bob_the_billboard_assembler.right(90)
    bob_the_billboard_assembler.fd(500)
    bob_the_billboard_assembler.right(90)
    bob_the_billboard_assembler.fd(125)
    bob_the_billboard_assembler.end_fill()

    bob_the_billboard_assembler.color("white")
    writer.screenwrite(
        bob_the_billboard_assembler, "WELCOME TO SPACE", 48, [0, 180], 0, False
    )

    bob_the_billboard_assembler.hideturtle()


def build_buildings(num_buildings):
    # turtle setup
    bob_the_builder = turtle.Turtle()

    # random building widths
    global building_widths
    building_widths = []
    for i in range(num_buildings):
        random_big_building = random.randint(1, 100)
        if random_big_building < 99:
            building_widths.append(random.randint(1, 100))
        else:
            building_widths.append(random.randint(1000, 10000))
    total = 0
    for i in building_widths:
        total += i
    building_widths = [i * 700 / total for i in building_widths]

    # draw buildings
    bob_the_builder.teleport(-350, -200)
    tell_facade_area = True
    for i in range(num_buildings):
        # building dimensions and color
        bob_the_builder.color(
            "black", random.choice([(52, 74, 110), (64, 44, 89), (16, 34, 99)])
        )
        building_height = random.randint(100, 350)
        area = int(building_height * building_widths[i])
        if area > 100000:
            bob_the_builder.color("black", (181, 181, 181))
        bob_the_builder.width(random.randint(1, 4))

        # draw building
        bob_the_builder.begin_fill()
        bob_the_builder.pendown()
        bob_the_builder.setheading(90)
        bob_the_builder.fd(building_height)
        bob_the_builder.right(90)
        bob_the_builder.fd(building_widths[i])
        bob_the_builder.right(90)
        bob_the_builder.fd(building_height)
        bob_the_builder.right(90)
        bob_the_builder.end_fill()
        turtle.update()

        # facade management
        # if tell_facade_area:
        #     os.system(
        #         f'say "This building\'s facade has an area of {str(area)} square pixels. "'
        #     )
        # if area > 100000:
        #     os.system(
        #         f'say "Congradulations! Because this building has an area greater than 100000 square pixels, it has been given a coat of special silver paint! "'
        #     )
        # if i == 0 and num_buildings > 1:
        #     tell_facade_area = input.get_announcement_consent()

    bob_the_builder.hideturtle()


def install_doors(num_buildings):
    # turtle setup
    bob_the_door_installer = turtle.Turtle()
    bob_the_door_installer.color("black", (64, 33, 16))
    bob_the_door_installer.width(2)

    # door dimensions
    global door_height
    door_height = 27
    global door_widths
    door_widths = []

    # install doors
    bob_the_door_installer.teleport(-350 + building_widths[0] * 1 / 5, -200)
    for i in range(num_buildings):
        bob_the_door_installer.pendown()

        # save door widths so that address writer can use them
        door_width = (
            building_widths[i] * 1 / 5 if building_widths[i] * 1 / 5 < 15 else 15
        )
        door_widths.append(door_width)

        # install the door
        bob_the_door_installer.begin_fill()
        bob_the_door_installer.pendown()
        bob_the_door_installer.setheading(90)
        bob_the_door_installer.fd(door_height)
        bob_the_door_installer.right(90)
        bob_the_door_installer.fd(door_width)
        bob_the_door_installer.right(90)
        bob_the_door_installer.fd(door_height)
        bob_the_door_installer.end_fill()
        turtle.update()

        # move to next building
        bob_the_door_installer.penup()
        bob_the_door_installer.left(90)
        if i < num_buildings - 1:
            bob_the_door_installer.fd(
                building_widths[i] * 4 / 5 - door_width + building_widths[i + 1] * 1 / 5
            )

    bob_the_door_installer.hideturtle()


def write_addresses(num_buildings, first_address):
    # turtle setup
    bob_the_address_writer = turtle.Turtle()
    bob_the_address_writer.speed(0)
    bob_the_address_writer.color("white")
    bob_the_address_writer.penup()

    # write addresses
    bob_the_address_writer.teleport(
        -350 + building_widths[0] * 1 / 5 + door_widths[0] * 1 / 2,
        -200 + door_height / 2,
    )
    for i in range(num_buildings):
        # write the address
        bob_the_address_writer.write(first_address + 2 * i, align="center")
        turtle.update()

        # move to next building
        if i < num_buildings - 1:
            bob_the_address_writer.fd(
                building_widths[i] * 4 / 5
                - door_widths[i] / 2
                + building_widths[i + 1] * 1 / 5
                + door_widths[i + 1] / 2
            )

    bob_the_address_writer.hideturtle()
