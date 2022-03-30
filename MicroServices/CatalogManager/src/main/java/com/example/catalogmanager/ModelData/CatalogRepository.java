package com.example.catalogmanager.ModelData;

import org.springframework.data.repository.CrudRepository;

public interface CatalogRepository extends CrudRepository<Catalog, Integer> {
    public Catalog getCatalogBynameCD(String nameCD);
    public Catalog getCatalogByIdCD(Integer idCD);
}
