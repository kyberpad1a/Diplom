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

@Route(value = "/user")

public class userPage extends AppLayout
//        implements BeforeEnterObserver
      {

    private final Tabs menu;
    private H1 viewTitle;
    @Autowired
    private HttpServletRequest req;
          @Autowired
          GoodRepository repository;

    public userPage() {


        setPrimarySection(AppLayout.Section.DRAWER);

        addToNavbar(true, createHeaderContent());

        menu = createMenu();
        addToDrawer(createDrawerContent(menu));
//        try{
//            int pageNumber = 1; //default page number
//            int pageSize = 1; //default page size
//
//            userGoodsPage goodsPage = new userGoodsPage(repository, pageNumber, pageSize);
//            setContent(goodsPage);
//
//        }catch (NullPointerException ex){
//            Notification.show("Товары отсутствуют", 10000, Notification.Position.BOTTOM_CENTER);
//        }
    }

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

    private Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(createMenuItems());
        return tabs;
    }

    private Component[] createMenuItems() {
        return new Tab[]{createTab("Товары", userGoodsPage.class),
                createTab("Корзина", shoppingCart.class),
                createTab("Профиль", UserDetails.class),
                //createTab("Франшизы", franchiseInfo.class),
                //createTab("Card List", CardListView.class),
                createTab("Log out", loginPage.class)};
    }

    private static Tab createTab(String text,
                                 Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    private String getCurrentPageTitle() {
        return getContent().getClass().getAnnotation(PageTitle.class).value();
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();


        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);

    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren()
                .filter(tab -> ComponentUtil.getData(tab, Class.class)
                        .equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

//    @Override
//    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (beforeEnterEvent.getNavigationTarget() != DeniedAccessView.class &&
//                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).noneMatch(role -> role.equals("USER"))) {
//            beforeEnterEvent.rerouteTo(DeniedAccessView.class);
//        }
//    }
}

