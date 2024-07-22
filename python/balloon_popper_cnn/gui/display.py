import tkinter as tk
from PIL import ImageTk
import pyautogui as r

# disable pyautogui safety features
r.PAUSE = 0
r.FAILSAFE = False
import display_modules.bounds as b
import display_modules.network as n

top_bar_height = 39  # apple = 28


# ————— display functions ————— #


def get_image(l, d):
    """Takes a screenshot of the user-specified region of the screen, saves it, and updates the canvas."""

    # take a screenshot of the specified region and save it
    image = r.screenshot(region=[l[0], l[1], d[0], d[1]])
    image.save("screen.png")

    # display image
    image = image.crop([0, top_bar_height, image.width, image.height])
    image = ImageTk.PhotoImage(image)
    root.image = image  # no idea why this has to be here, something to do with tkinter garbage collection
    canvas.create_image(0, 0, anchor="nw", image=image)


def pop_balloons(balloon_positions):
    """Draws bounding boxes on the balloons, and, if robot is turned on, moves the mouse to the balloons and clicks them."""

    for i in balloon_positions:
        # draw rectangle bounding boxes
        x1 = i[0]
        y1 = i[1] - top_bar_height
        x2 = i[0] + i[2]
        y2 = i[1] + i[3] - top_bar_height
        canvas.create_rectangle(x1, y1, x2, y2, outline="#ff0000")

        # move the mouse and click on balloons while maintaining focus on the display
        if robot_on:
            r.moveTo(
                (x1 + x2) / 2,
                (y1 + y2) / 2,
            )
            r.tripleClick()
            root.focus_force()


def update(l, d):
    global after

    get_image(l, d)
    pop_balloons(n.get_predictions())

    after = root.after(500, lambda: update(l, d))  # calls itself after half a second


# ————— keybinding functions ————— #


def reset_bounds(event):
    global robot_on

    robot_on = False
    b.create_bounds_window(root)
    robot_on = True


def switch_robot(event):
    global robot_on

    robot_on = not robot_on


def close_window(event):
    root.after_cancel(after)
    root.destroy()


# ————— display window ————— #


def display_ai_output(lefttop=[0, 0], dimensions=[r.size()[0] // 2, r.size()[1]]):
    """Creates the window for displaying the ai output."""

    global root, canvas, robot_on

    # create the tkinter window
    root = tk.Tk()
    root.title("ai_output")
    root.geometry(
        f"{dimensions[0]}x{dimensions[1] - top_bar_height}+{r.size()[0]-dimensions[0]}+0"
    )
    root.focus_set()
    root.lift()

    # keybindings
    root.bind("<b>", reset_bounds)
    root.bind("<r>", switch_robot)
    root.bind("<p>", close_window)

    # start the canvas display
    canvas = tk.Canvas(width=dimensions[0], height=dimensions[1], highlightthickness=0)
    canvas.grid(row=0, column=0)
    robot_on = False
    update(lefttop, dimensions)

    root.mainloop()
