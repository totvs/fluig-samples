<!-- Slot -->
<div class="layout-clean fluig-style-guide height-full">
	<div id="SlotA" class="height-full" slot="true" decorator="false" editableSlot="false">
	<#list (pageRender.getInstancesIds("SlotA"))! as id>
		${pageRender.renderInstanceNoDecorator(id)}
	</#list>
	</div>
</div>