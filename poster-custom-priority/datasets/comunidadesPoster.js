function createDataset(fields, constraints, sortFields) {
	var dataset = DatasetBuilder.newDataset();
    
    //Cria as colunas
    dataset.addColumn("optionalOp");
    
    //Cria os registros
    dataset.addRow(new Array("optional"));
     
    return dataset;

}