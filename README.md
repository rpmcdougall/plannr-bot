[![CircleCI](https://circleci.com/gh/rpmcdougall/plannr-bot.svg?style=svg)](https://circleci.com/gh/rpmcdougall/plannr-bot)

# plannr-bot

Plannr Bot is a discord bot for scheduling events and enabling participation by allowing users to sign up for events.

### Architecture

Documentation on architecture of plannr-bot and interaction with supporting services can be found [here](doc/architecture.md).

## Development Environment Setup

### PostgresSQL / Redis

Use the included docker-compose file to start up a Redis and PostgreSQL instance using `docker-compose up -d` from the root of the project.

### Dependencies

To get dependencies simply use `lein install` to fetch them.

### Configuration

#### App Configuration

plannr-bot uses [yogthos/config](https://github.com/yogthos/config) for managing app config. Profiles are created in `project.clj` under the key `profiles`. You can provide additional profiles by listing them with a new key and a resource path as shown below. This correlates to the path `config/<profile>` from the root of the project. The library primarily supports `.edn` files and environment vars. Visit the repo link above for further documentation on configuration.
```
:profiles {:uberjar {:aot :all}
             :prod {:resource-paths ["config/prod"]}
             :dev  {:resource-paths ["config/dev"]}})
```

#### discord.clj Configuration

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

#### SQL Query Configuration
plannr-bot uses [hugsql](https://github.com/layerware/hugsql) for interacting with PostgreSQL. To add additional queries to parse in as functions, you can add additional queries to existing files located [here](src/plannr_bot/db/sql). If any additional query files are added, make sure to import them [here](src/plannr_bot/db/sql.clj) using `hugsql/def-db-fns`.


## Usage

### Available Commands
Plan Event: Creates a new event and PM's the scheduler back with the details.
```
!plan-event <event-name> | <start date/time>
Example: !plan-event Fragfest | 12/10/2019 05:00
```

Join Event: Allows user to add themselves as an attendee to an event and PM's the attendee back with the details
```
!join-event <event-name>
Example !join-event Hackathon December 2019
```

List Events: Allows user to list all available events in plannr-bot and PM's the requestor back with the details
```
!list-events 
Example !list-events
```

Cancel Event: Allows user to cancel event and PM's the scheduler back with the details
```
!cancel-event <event-name>
Example !join-event Hackathon December 2019
```

