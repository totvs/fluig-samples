function defineStructure() {
    addColumn("ID_FORNECEDOR");
    addColumn("CNPJ");
    addColumn("NOME_FANTASIA");
    addColumn("RAZAO_SOCIAL");
    addColumn("ENDERECO");
    addColumn("TELEFONE");
    addColumn("EMAIL");
    addColumn("STATUS");
}

function onSync(lastSyncDate) {
    return createDataset();
}

function createDataset(fields, constraints, sortFields) {
    var dataset = DatasetBuilder.newDataset();

    dataset.addColumn("ID_FORNECEDOR");
    dataset.addColumn("CNPJ");
    dataset.addColumn("NOME_FANTASIA");
    dataset.addColumn("RAZAO_SOCIAL");
    dataset.addColumn("ENDERECO");
    dataset.addColumn("TELEFONE");
    dataset.addColumn("EMAIL");
    dataset.addColumn("STATUS");

    dataset.addRow(["3", "33.333.333/0001-33", "Fornecedor C", "Empresa C LTDA", "Rua C, 789", "11777777777", "contato@c.com", "ATIVO"]);
    dataset.addRow(["4", "44.444.444/0001-44", "Fornecedor D", "Empresa D SA", "Avenida D, 101", "11666666666", "contato@d.com", "ATIVO"]);
    dataset.addRow(["5", "55.555.555/0001-55", "Fornecedor E", "Empresa E ME", "Rua E, 202", "11555555555", "contato@e.com", "INATIVO"]);
    dataset.addRow(["6", "66.666.666/0001-66", "Fornecedor F", "Empresa F LTDA", "Avenida F, 303", "11444444444", "contato@f.com", "ATIVO"]);
    dataset.addRow(["7", "77.777.777/0001-77", "Fornecedor G", "Empresa G EPP", "Rua G, 404", "11333333333", "contato@g.com", "ATIVO"]);
    dataset.addRow(["8", "88.888.888/0001-88", "Fornecedor H", "Empresa H SA", "Avenida H, 505", "11222222222", "contato@h.com", "ATIVO"]);
    dataset.addRow(["9", "99.999.999/0001-99", "Fornecedor I", "Empresa I LTDA", "Rua I, 606", "11111111111", "contato@i.com", "INATIVO"]);
    dataset.addRow(["10", "10.101.010/0001-10", "Fornecedor J", "Empresa J ME", "Avenida J, 707", "11910101010", "contato@j.com", "ATIVO"]);
    dataset.addRow(["11", "12.121.212/0001-12", "Fornecedor K", "Empresa K SA", "Rua K, 808", "11912121212", "contato@k.com", "ATIVO"]);
    dataset.addRow(["12", "13.131.313/0001-13", "Fornecedor L", "Empresa L LTDA", "Avenida L, 909", "11913131313", "contato@l.com", "ATIVO"]);
    dataset.addRow(["13", "14.141.414/0001-14", "Fornecedor M", "Empresa M EPP", "Rua M, 1010", "11914141414", "contato@m.com", "INATIVO"]);
    dataset.addRow(["14", "15.151.515/0001-15", "Fornecedor N", "Empresa N SA", "Avenida N, 1111", "11915151515", "contato@n.com", "ATIVO"]);
    dataset.addRow(["15", "16.161.616/0001-16", "Fornecedor O", "Empresa O LTDA", "Rua O, 1212", "11916161616", "contato@o.com", "ATIVO"]);
    dataset.addRow(["16", "17.171.717/0001-17", "Fornecedor P", "Empresa P ME", "Avenida P, 1313", "11917171717", "contato@p.com", "ATIVO"]);
    dataset.addRow(["17", "18.181.818/0001-18", "Fornecedor Q", "Empresa Q SA", "Rua Q, 1414", "11918181818", "contato@q.com", "INATIVO"]);

    return dataset;
}

function onMobileSync(user) {
}
