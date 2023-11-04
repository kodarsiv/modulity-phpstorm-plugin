# Modulity IDE Plugin

Modulity IDE Plugin is an extension for the Modulity package, bringing the powerful command-line functionalities of Modulity directly into your favorite Integrated Development Environment (IDE). This plugin allows PHP API developers to harness the modular design capabilities of Modulity within the Laravel framework, directly from the IDE interface.

With this plugin, you can streamline your workflow by generating modules, services, repositories, and controllers through a simple right-click in the IDE's sidebar.
It provides a convenient way to interact with Modulity's command-line instructions without leaving your development environment.

## Features

- **Seamless Integration:** Works within the IDE, integrating with the Laravel Artisan commands.
- **Contextual Generation:** Right-click on the sidebar to access Modulity features in the "Generate with Modulity" context menu.
- **Interactive Forms:** Fill out forms with ease to execute commands for creating modules, services, and more.
- **Efficiency Boost:** Reduces the need for repetitive tasks, accelerating the project development process.

## How to Use

1. **Right-click** on the sidebar within your IDE.
2. Navigate to **New > Generate with Modulity**.
3. Select the desired command from the menu to open a form.
4. Fill in the form details and the plugin will execute the corresponding Modulity command.

## Installation

This plugin requires the Modulity package to be installed in your Laravel project. If you haven't already, follow the Modulity package installation instructions:

```shell
composer require tanerincode/modulity
```

Then, publish the configuration file:
```shell
php artisan vendor:publish --provider="Kodarsiv\Modulity\Providers\ModulityServiceProvider" --tag=modulity-config
```
After setting up the Modulity package, install the Modulity IDE Plugin through your IDE's plugin marketplace.

### Support
If you encounter any issues or have questions, please file them in the issues section of the repository, or contact tombastaner@gmail.com

### Contribution
Contributions are welcome. Please fork the repository and submit a pull request with your improvements.

License
Modulity IDE Plugin is open-sourced software licensed under the [MIT license](#).

![create_service.png](docs%2Fcreate_service.png)
![open_menu.png](docs%2Fopen_menu.png)