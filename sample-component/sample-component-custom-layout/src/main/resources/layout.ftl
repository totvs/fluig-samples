<#import "/wcm.ftl" as wcm/>
<@wcm.header />

<!-- WCM Wrapper content -->
<div class="wcm-wrapper-content fulllayout" id="oem">
    
    <!-- Wrapper -->    
    <#if pageRender.isEditMode()=true>            
        <div id="edicaoPagina" class="clearfix">
    <#else>
        <div id="visualizacaoPagina" class="clearfix">
    </#if>                
            <div id="oem-side-nav" class="side-nav">                
                <@wcm.renderSlot id="SlotA" editableSlot="true" />
            </div>
            <div id="oem-main-nav"  class="main-nav">                
                <@wcm.renderSlot id="SlotB" editableSlot="true" />
            </div>
            
        </div>    
    
</div>