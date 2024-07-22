import tkinter as tk
from tkinter.font import Font

import display as d


def set_bounds():
    """
    Sets the return variables for the bounds window and creates \n
    a new display window with the newly specified bounds. \n

    This function is called when the user destroyes the bounds window.
    """

    lefttop = [bounds_window.winfo_rootx(), bounds_window.winfo_rooty() - 28]
    dimensions = [bounds_window.winfo_width(), bounds_window.winfo_height() + 28]

    bounds_window.destroy()

    # create another root window with the new bounds size
    d.root.after_cancel(d.after)
    d.root.destroy()
    d.lefttop = lefttop
    d.dimensions = dimensions
    d.display_ai_output(lefttop=lefttop, dimensions=dimensions)


def create_bounds_window(root):
    """Allows the user to change size of the window that is taken as input."""

    global bounds_window

    bounds_window = tk.Toplevel()
    bounds_window.title("bounds_window")
    width = root.winfo_screenwidth() // 2
    height = root.winfo_screenheight() // 2
    bounds_window.geometry(f"{width}x{height - 28}+{width//2}+{height//2}")
    bounds_window.attributes("-alpha", 0.6)
    bounds_window.protocol("WM_DELETE_WINDOW", set_bounds)

    f = Font(family="Times New Roman", size=36, weight="normal")
    instructions = tk.Label(bounds_window, text="Resize window; set bounds. ", font=f)
    instructions.place(relx=0.5, rely=0.5, anchor=tk.CENTER)

    bounds_window.mainloop()
