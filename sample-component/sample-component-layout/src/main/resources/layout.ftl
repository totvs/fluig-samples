<#import "/wcm.ftl" as wcm/>

<#-- Variaveis globais para os layouts -->
<#import "/layout-globals.ftl" as globals />

<#if pageRender.isPreviewMode() = true>
	<@wcm.previewPageAlert />
	<@wcm.deviceTogglePreview />
</#if>

<!-- WCM Wrapper content -->
<div class="wcm-wrapper-content ${wcmLayoutEditClass!""} ${pageAuthTypeClass!""}">

    <#if pageRender.isEditMode() != true>
        <@wcm.header />
        <@wcm.menu />
    </#if>

    <!-- Wrapper -->
    <div class="wcm-all-content">

        <div id="wcm-content" class="clearfix wcm-background">

            <#if pageRender.isEditMode() = true>
                <@wcm.editHeader />
                <@wcm.widgetsList />
            </#if>

            <div id="${divMasterId!""}" class="clearfix">

                <!-- Slot 1 -->
                <div class="editable-slot slotfull layout-1-1" id="slotFull2">
                    <@wcm.renderSlot id="SlotA" editableSlot="true" isResponsiveSlot="true" />
                </div>
            
                <#if fluigThemeCode != "responsive_theme">
                    <@wcm.footer layoutuserlabel="wcm.layoutsimples.user" />
                </#if>
            </div>
        </div>
	</div>
</div>