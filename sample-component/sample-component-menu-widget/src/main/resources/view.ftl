<div id="widgetMenu_${instanceId}" class="fluig-style-guide wcm-widget-class super-widget" data-params="WidgetMenu.instance()">
    
    <ul id="widgetmenu-menu-items" class="nav">
        <li class="menu-item">&nbsp;</li>
        <li class="menu-item<#if pageCode == 'home'> active</#if>">
            <a class="item-a" href="${tenantURI}/home"> 
                <span class="fluigicon fluigicon-home fluigicon-md"></span>
                <span class="menu-item-text">${i18n.getTranslation('menu.home')}</span>
            </a>
        </li>
        <li class="menu-item<#if pageCode == 'page_custom_layout'> active</#if>" >
            <a class="item-a" href="${tenantURI}/page_custom_layout">
                <span class="fluigicon fluigicon-android fluigicon-md"></span>
                <span class="menu-item-text">${i18n.getTranslation('page.custom.layout')}</span>
            </a>
        </li>
        
        <hr> <!-- linha horizontal -->                
        <li class="menu-item<#if pageCode == 'ecmnavigation'> active</#if>" >
            <a class="item-a" href="${tenantURI}/ecmnavigation">
                <span class="fluigicon fluigicon-files fluigicon-md"></span>
                <span class="menu-item-text">${i18n.getTranslation('menu.documents')}</span>
            </a>
        </li>
        <li class="menu-item<#if pageCode == 'pagerecyclebin'> active</#if>" >
            <a class="item-a" href="${tenantURI}/pagerecyclebin">
                <span class="fluigicon fluigicon-trash fluigicon-md"></span>
                <span class="menu-item-text">${i18n.getTranslation('menu.trash')}</span>
            </a>
        </li>
        <li class="menu-item">
            <a class="item-a" href="${tenantURI}/cpaneladmin">
                <span class="fluigicon fluigicon-cog fluigicon-md"></span>
                <span class="menu-item-text">${i18n.getTranslation('menu.control.panel')}</span>
            </a>
        </li>

        <hr> <!-- linha horizontal -->

        <li class="menu-item">
            <a class="item-a" href="#">
                <img src="/widgetmenu/resources/images/menu.png" >
                <span class="menu-item-img-text">${i18n.getTranslation('menu.img')}</span>
            </a>
        </li>
    </ul>
    
</div>