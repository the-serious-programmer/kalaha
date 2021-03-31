# Kalaha front-end

The front-end is build with Angular and originally generated with [Angular CLI](https://github.com/angular/angular-cli)
version 11.1.4.  
The current used version of Angular can be found in the `package.json` file.

## Prerequisites

To start developing and to run the application you can
read [this official Angular setup guide](https://angular.io/guide/setup-local). In short, you will need:

- [NodeJS](https://nodejs.org/en/)
- [Npm](https://www.npmjs.com/)
- Angular-CLI

A useful tool to install and manage NodeJS- and NPM-versions can
be [Node Version Manager (NVM)](https://github.com/nvm-sh/nvm/blob/master/README.md).

## Install the app

You can install the project by running `npm install` in the front-end root folder.  
After this you can run several commands with `npm run <COMMAND, for instance e2e>`, see the `package.json` file for an
updated list of commands that can be run.

## Running the application

After installing the project you can run the front-end in one way:

### Test/Development mode

Run `ng serve` for a development server.  
Navigate to `http://localhost:4200/` and the app will try to create a game on opening the app.  
For this to work, the back-end and database where the front-end depend on needs to run as well. See `back-end/README.md`
for more information.  
The app will automatically reload if you change any of the source files.

## Code generation

Run `ng generate component component-name` to generate a new component.  
You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project.  
The build artifacts will be stored in the `dist/` directory.  
Use the `--prod` flag for a production build.

The `dist/` folder can later be used to make a production environment, for instance with
a [Docker](https://www.docker.com/get-started) container.  
Also see `documentation/IMPROVEMENTS.md`

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).  
To see the code coverage you can run `ng test --no-watch --code-coverage`, which will create a `code-coverage/` folder
in the root folder of the front-end with an `index.html` file which you can view in your browser.
See [this link](https://angular.io/guide/testing-code-coverage) for more information.

## Run linting

Run `ng lint` to execute linting, set-up with [TSLint](https://palantir.github.io/tslint/).  
Note that with installing the project, a git pre-commit hook for [Prettier](https://prettier.io) is automatically
installed with [Husky](https://typicode.github.io/husky) to format the front-end code automatically before committing.  
You can also run this auto-format: `npm run format:fix`

## Further help

To get more help on the Angular CLI use `ng help` or go check out
the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.
