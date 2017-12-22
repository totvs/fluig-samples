function setSelectedZoomItem(selectedItem){
	if (selectedItem.type == "folder") {
		document.getElementById("folderID").value = selectedItem.documentId;
		document.getElementById("folderName").value = selectedItem.documentDescription;
	}
}

function abrirZoom(){
	var TITULO = "Selecione a pasta";
	var DATASET = "document";
	var COLUNAS = "documentId," + escape('Código') + ",documentDescription," + escape('Descrição') ;
	var RESULTADO = "documentId,documentDescription";
	var TIPO = "folder";	
	var FILTRO = "documentType,1"; 
	openZoom(TITULO, DATASET, COLUNAS, RESULTADO, TIPO, "600", "350", FILTRO);	
}

function openZoom(title, dataset, dataFields, resultFields, type, width, height, filterValues){
	window.open("/webdesk/zoom.jsp?datasetId=" + dataset + "&dataFields=" + dataFields + "&resultFields=" + resultFields + "&type=" + type + "&title=" + title + "&filterValues=" + filterValues, "zoom" , "status=no , scrollbars=no ,width="+width+", height="+height+" , top=0 , left=0");
}