[![CircleCI](https://circleci.com/gh/rpmcdougall/plannr-bot.svg?style=svg)](https://circleci.com/gh/rpmcdougall/plannr-bot)

# plannr-bot

Plannr Bot is a discord bot for scheduling events and enabling participation by allowing users to sign up for events.

## Development Environment Setup

### Dependencies

To get dependencies simply use `lein install` to fetch them.


### discord.clj Configuration

`discord.clj` requires having a settings.json file with the following format at the location `data/settings` from the root of the project. This will need to be created/generated before running the project so it can connect to the Discord API.

```json
{
    "token" : "Your Auth Token",
    "prefix" : "!",
    "bot-name" : "CoolDiscordBot",
    "extension-folders" : [
        "src/discord/extensions"
    ]
}
```

More documentation on the options can be found [here](https://github.com/gizmo385/discord.clj/blob/master/docs/bot-configuration.md).

## Usage

TODO



