package com.example.diplom.view.goods;

import com.example.diplom.view.DeniedAccessView;
import com.example.diplom.view.auth.loginPage;
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
import com.vaadin.flow.router.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
/**
 * Класс страницы товаров, унаследованный от AppLayout и реализующий интерфейс BeforeEnterObserver.
 * Обеспечивает доступ к странице только пользователям с ролью GOODSSTAFF.
 */
@Route(value = "/goods")
@RouteAlias(value = "/")
public class goodsPage extends AppLayout implements BeforeEnterObserver {

    /**
     * Табы меню навигации по странице товаров.
     */
    private final Tabs menu;
    /**
     * Заголовок страницы.
     */
    private H1 viewTitle;

    /**
     * Создает новый объект класса и настраивает вид страницы.
     */
    public goodsPage() {
        setPrimarySection(AppLayout.Section.DRAWER);

        addToNavbar(true, createHeaderContent());

        menu = createMenu();
        addToDrawer(createDrawerContent(menu));
    }

    /**
     * Создает содержимое шапки страницы.
     * @return компонент горизонтальной компоновки.
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
     * Создает содержимое боковой панели с табами меню.
     * @param menu - компонент табов меню.
     * @return компонент вертикальной компоновки.
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
     * Создает табы меню.
     * @return компонент табов меню.
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
     * Создает элементы табов меню.
     * @return массив компонентов табов меню.
     */
    private Component[] createMenuItems() {
        return new Tab[] { createTab("Товары", goodsInfo.class),
                createTab("Категории", categoryInfo.class),
                createTab("Франшизы", franchiseInfo.class),
                createTab("Логин", loginPage.class) };
    }

    /**
     * Создает таб с навигационной ссылкой на страницу.
     * @param text - текст названия страницы.
     * @param navigationTarget - класс страницы.
     * @return экземпляр класса Tab.
     */
    private static Tab createTab(String text,
                                 Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    /**
     * Получает текущий заголовок страницы.
     * @return строку с заголовком страницы.
     */
    private String getCurrentPageTitle() {
        return getContent().getClass().getAnnotation(PageTitle.class).value();
    }

    /**
     * Вызывается после навигации на новую страницу и обновляет заголовок и выбранный таб меню.
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

    /**
     * Выполняется перед входом на страницу приложения
     *
     * @param beforeEnterEvent событие, вызванное перед входом на страницу
     */
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (beforeEnterEvent.getNavigationTarget() != DeniedAccessView.class &&

                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).noneMatch(role -> role.equals("GOODSSTAFF"))) {
            beforeEnterEvent.rerouteTo(DeniedAccessView.class);
        }
    }
    }

