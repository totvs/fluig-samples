<#import "/wcm.ftl" as wcm/>

<#-- Variaveis globais para os layouts -->
<#import "/layout-globals.ftl" as globals />

<#if pageRender.isPreviewMode() = true>
	<@wcm.previewPageAlert />
	<@wcm.deviceTogglePreview />
</#if>

<!-- WCM Wrapper content -->
<div class="wcm-wrapper-content ${wcmLayoutEditClass!""} ${pageAuthTypeClass!""}" id="oem">
    
    <#if pageRender.isEditMode() != true>
        <@wcm.header />
    </#if>

    <div class="wcm-all-content">
        
        <div id="wcm-content" class="clearfix wcm-background">

            <#if pageRender.isEditMode() = true>
                <@wcm.editHeader />
                <@wcm.widgetsList />
            </#if>

            <div id="${divMasterId!""}" class="clearfix container-columns">

                <div id="oem-side-nav" class="side-nav">
                    <div class="editable-slot slotfull layout-1-1" id="SlotA">
                        <@wcm.renderSlot id="SlotA" editableSlot="true" isResponsiveSlot="true" />
                    </div>
                </div>

                <div id="oem-main-nav"  class="main-nav">
                    <div class="editable-slot slotfull layout-1-1" id="SlotB">
                        <@wcm.renderSlot id="SlotB" editableSlot="true" isResponsiveSlot="true" />
                    </div>
                </div>
            </div>
        </div>
    </div>    
</div>