package com.example.diplom.view.management;

import com.example.diplom.view.auth.loginPage;
import com.example.diplom.view.user.shoppingCart;
import com.example.diplom.view.user.userGoodsPage;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.Optional;

/**
 * Страница управления.
 */
@Route(value = "/management")
public class managementPage extends AppLayout {

    /**
     * Меню страницы.
     */
    private final Tabs menu;

    /**
     * Заголовок страницы.
     */
    private H1 viewTitle;

    /**
     * Конструктор страницы управления.
     */
    public managementPage() {
        setPrimarySection(AppLayout.Section.DRAWER);

        addToNavbar(true, createHeaderContent());

        menu = createMenu();
        addToDrawer(createDrawerContent(menu));
    }

    /**
     * Создание содержимого шапки страницы.
     * @return содержимое макета.
     */
    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();


        layout.setId("header");
        layout.getThemeList().set("light", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);


        layout.add(new DrawerToggle());

        viewTitle = new H1();
        layout.add(viewTitle);



        return layout;
    }

    /**
     * Создание содержимого боковой панели страницы.
     * @param menu меню страницы.
     * @return содержимое макета.
     */
    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();


        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);


        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        Image IMG = new Image("images/logo4.png", "My Project logo");
        IMG.setHeight("103px");
        IMG.setWidth("103px");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(IMG);
        logoLayout.add(new H2("Логово лича"));


        layout.add(logoLayout, menu);
        return layout;
    }

    /**
     * Создание меню страницы.
     * @return меню.
     */
    private Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(createMenuItems());
        return tabs;
    }

    /**
     * Создание элементов меню страницы.
     * @return массив элементов меню.
     */
    private Component[] createMenuItems() {
        return new Tab[] { createTab("Пользователи", userGoodsPage.class),
                createTab("Log out", loginPage.class) };
    }

    /**
     * Создание вкладки меню.
     * @param text текст вкладки.
     * @param navigationTarget класс содержимого, связанного с вкладкой.
     * @return вкладка.
     */
    private static Tab createTab(String text,
                                 Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    /**
     * Получение текущего заголовка страницы.
     * @return текущий заголовок.
     */
    private String getCurrentPageTitle() {
        return getContent().getClass().getAnnotation(PageTitle.class).value();
    }

    /**
     * Обработчик события после перехода на другую страницу.
     */
    @Override
    protected void afterNavigation() {
        super.afterNavigation();


        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);


        viewTitle.setText(getCurrentPageTitle());
    }
    /**
     * Получение вкладки для компонента
     *
     * @param component компонент для которого нужно получить соответствующую вкладку
     * @return объект Optional, содержащий соответствующую вкладку, или пустой Optional, если такой вкладки не существует
     */
    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren()
                .filter(tab -> ComponentUtil.getData(tab, Class.class)
                        .equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

}
