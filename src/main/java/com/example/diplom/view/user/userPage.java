package com.example.diplom.view.user;

import com.example.diplom.repo.GoodRepository;
import com.example.diplom.view.DeniedAccessView;
import com.example.diplom.view.auth.loginPage;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
/**
 * Класс отображения страницы пользователя,
 * наследуется от Vaadin AppLayout и содержит методы для создания верхнего меню, боковой панели и т. д.
 * */
@Route(value = "/user")

public class userPage extends AppLayout{
        /**
         * Меню с закладками для переключения между страницами
         */
        private final Tabs menu;

        /**
         * Заголовок текущей открытой страницы
         */
        private H1 viewTitle;

        /**
         * Автоматически связывает экземпляр, когда он создается,
         * используя конструктор по умолчанию, с запросом пользователя, который привел к созданию этого экземпляра.
         */
        @Autowired
        private HttpServletRequest req;

        /**
         * Репозиторий товара, автоматически внедряемый при помощи Spring's Dependency Injection
         */
        @Autowired
        GoodRepository repository;

        /**
         * Конструктор класса, который вызывает методы создания верхнего меню и боковой панели
         */
    public userPage() {


        setPrimarySection(AppLayout.Section.DRAWER);

        addToNavbar(true, createHeaderContent());

        menu = createMenu();
        addToDrawer(createDrawerContent(menu));
    }
    /**
     * Создает содержимое верхней панели, включая кнопку переключения боковой панели
     * @return компонент горизонтального макета
     */
    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();


        layout.setId("header");
        layout.getThemeList().set("light", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        layout.add(new DrawerToggle());


        return layout;
    }
    /**
     * Создает содержимое боковой панели с заданным меню
     * @param menu экземпляр Tabs, содержащий закладки меню
     * @return компонент вертикального макета
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
     * Метод создает вертикальное меню (Tabs) для навигации по страницам приложения.
     *
     * @return Tabs вертикальное меню (Tabs)
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
     * Метод создает массив элементов (Component), которые будут использоваться в меню.
     *
     * @return Component[] массив элементов (Tab) для меню.
     */
    private Component[] createMenuItems() {
        return new Tab[]{createTab("Товары", userGoodsPage.class),
                createTab("Корзина", shoppingCart.class),
                createTab("Профиль", UserDetails.class),
                createTab("Логин", loginPage.class)};
    }
    /**
     * Метод создает пункт меню (Tab) с указанным текстом и адресом навигации.
     *
     * @param text текст, отображаемый на пункте меню (Tab)
     * @param navigationTarget адрес навигации, куда будет направляться пользователь при выборе пункта меню (Tab)
     * @return Tab пункт меню (Tab) с указанным текстом и адресом навигации.
     */
    private static Tab createTab(String text,
                                 Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }
    /**
     * Метод возвращает заголовок текущей страницы.
     *
     * @return String заголовок текущей страницы.
     */
    private String getCurrentPageTitle() {
        return getContent().getClass().getAnnotation(PageTitle.class).value();
    }
    /**
     * Метод вызывается после навигации пользователя по приложению. Проверяет, какая страница открыта, и устанавливает ее пункт меню (Tab) в качестве выбранного.
     */
    @Override
    protected void afterNavigation() {
        super.afterNavigation();


        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);

    }
    /**
     * Метод возвращает пункт меню (Tab), связанный с данной страницей компонента.
     *
     * @param component компонент страницы, для которой нужно найти соответствующий пункт меню (Tab)
     * @return Optional<Tab> пункт меню (Tab), связанный с данной страницей компонента.
     */
    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren()
                .filter(tab -> ComponentUtil.getData(tab, Class.class)
                        .equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

}

