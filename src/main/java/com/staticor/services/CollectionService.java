package com.staticor.services;

import com.staticor.models.collections.Collection;
import com.staticor.models.connections.Connection;
import com.staticor.models.connections.Database;
import com.staticor.models.dtos.CollectionInitialization;
import com.staticor.repositories.CollectionRepository;
import com.staticor.repositories.DatabaseRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionService extends ServiceResponse {

    private final static Logger LOGGER = LogManager.getLogger(CollectionService.class);

    private final CollectionRepository repository;

    private final DatabaseRepository databaseRepository;


    public CollectionService(CollectionRepository repository,
                             DatabaseRepository databaseRepository) {
        this.repository = repository;
        this.databaseRepository = databaseRepository;
    }

    public ServiceResponse createCollection(CollectionInitialization init) {

        try {
            Optional<Database> database = databaseRepository.findById(init.getDatabaseId());
            database.orElseThrow(RuntimeException::new);

            // collection data
            Collection collection = new Collection();
            collection.setUserId(init.getUserId());
            collection.setName(init.getCollectionName());
            collection.setDescription(init.getCollectionDesc());
            collection.setUrl(collection.getName().toLowerCase().replaceAll("\\s", "-"));

            // connection data
            Connection connection = new Connection();
            connection.setDatabase(database.get());
            connection.setName(init.getConnectionName());
            connection.setDescription(init.getConnectionDesc());
            connection.setDatabaseName(init.getConnectionDbName());
            connection.setHost(init.getConnectionHost());
            connection.setPort(init.getConnectionPort());
            connection.setUsername(init.getConnectionUsername());
            connection.setPassword(init.getConnectionPassword());

            collection.setConnection(connection);

            repository.save(collection);

            return success(true).code(200).message("Congratulation, your collection successfully created!");
        } catch (Exception e) {
            return success(false).code(500).errors(e.getLocalizedMessage());
        }
    }

    public ServiceResponse getCollectionByUserId(String username) {
        try {
            Optional<List<Object>> all = repository.getAllByUserId(username);
            all.orElseThrow(RuntimeException::new);

            return success(true).code(200).result(all.get());
        } catch (Exception e) {
            return success(false).code(500).errors(e.getLocalizedMessage());
        }
    }
}