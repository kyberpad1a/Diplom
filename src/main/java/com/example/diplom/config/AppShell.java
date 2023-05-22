package com.example.diplom.config;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * Класс AppShell.
 * Реализует интерфейс AppShellConfigurator, который настраивает приложение на основе Vaadin.
 * Определяет тему оформления по умолчанию для всех страниц приложения.
 * В данном случае используется тема Lumo.DARK.
 */
@Theme(variant = Lumo.DARK)
public class AppShell implements AppShellConfigurator {

}
