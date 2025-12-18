import os
import asyncio
import discord
from discord import app_commands

# Get token from environment variable
TOKEN = os.getenv("DISCORD_TOKEN")

if TOKEN is None:
    raise RuntimeError("DISCORD_TOKEN environment variable not set")

class ReminderBot(discord.Client):
    def __init__(self):
        intents = discord.Intents.default()
        super().__init__(intents=intents)
        self.tree = app_commands.CommandTree(self)

    async def setup_hook(self):
        # Sync slash commands globally
        await self.tree.sync()

bot = ReminderBot()

@bot.event
async def on_ready():
    print(f"✅ Logged in as {bot.user}")

@bot.tree.command(name="remind", description="Send a reminder after some minutes (via DM)")
@app_commands.describe(
    user="Who should receive the reminder",
    minutes="How many minutes from now",
    message="The reminder message"
)
async def remind(
    interaction: discord.Interaction,
    user: discord.User,
    minutes: int,
    message: str
):
    # Acknowledge privately
    await interaction.response.send_message(
        f"⏰ Reminder set for {user.mention} in {minutes} minutes.",
        ephemeral=True
    )

    # Wait asynchronously
    await asyncio.sleep(minutes * 60)

    # Try to DM the user
    try:
        await user.send(f"🔔 Reminder: {message}")
    except discord.Forbidden:
        await interaction.followup.send(
            "❌ I couldn't DM that user (DMs disabled or bot blocked).",
            ephemeral=True
        )

bot.run(TOKEN)
