package com.staticor.services;

import com.staticor.exceptions.CollectionNotFoundException;
import com.staticor.models.collections.Collection;
import com.staticor.models.connections.Connection;
import com.staticor.models.connections.Database;
import com.staticor.models.dtos.CollectionInitializationDto;
import com.staticor.models.dtos.ReportCreateDto;
import com.staticor.models.dtos.ViewCollectionDto;
import com.staticor.models.reports.Report;
import com.staticor.repositories.CollectionRepository;
import com.staticor.repositories.ConnectionRepository;
import com.staticor.repositories.DatabaseRepository;
import com.staticor.repositories.ReportRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CollectionService extends ServiceResponse {

    private final static Logger LOGGER = LogManager.getLogger(CollectionService.class);

    private final CollectionRepository repository;

    private final DatabaseRepository databaseRepository;

    private final ReportRepository reportRepository;

    private final CollectionRepository collectionRepository;

    private final ConnectionRepository connectionRepository;

    public CollectionService(CollectionRepository repository,
                             DatabaseRepository databaseRepository,
                             ReportRepository reportRepository,
                             CollectionRepository collectionRepository,
                             ConnectionRepository connectionRepository) {
        this.repository = repository;
        this.databaseRepository = databaseRepository;
        this.reportRepository = reportRepository;
        this.collectionRepository = collectionRepository;
        this.connectionRepository = connectionRepository;
    }

    public ServiceResponse createCollection(CollectionInitializationDto init) {

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

            return success(true).code(200).message("Congratulation, your collection successfully created!").result(collection);
        } catch (DataIntegrityViolationException e) {
            return success(false).code(500).errors(e.getCause().getMessage());
        } catch (Exception e) {
            return success(false).code(500).errors(e.getCause().getMessage());
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

    public ServiceResponse getReportsByCollectionId(Long collectionId) {
        try {
            // get collection
            Optional<Collection> collection = repository.findById(collectionId);
            collection.orElseThrow(CollectionNotFoundException::new);

            List<Report> reports = reportRepository.getAllByCollection(collection.get());

            Stream<ReportCreateDto> reportCreateDtoStream = reports.stream().map(report -> {
                ReportCreateDto createDto = new ReportCreateDto();
                createDto.setReportId(report.getId());
                createDto.setCollectionId(report.getCollection().getId());
                createDto.setReportName(report.getName());
                createDto.setReportDesc(report.getDescriptions());
                return createDto;
            });

            return success(true).code(200).result(reportCreateDtoStream);
        } catch (Exception e) {
            return success(false).code(500).errors(e.getLocalizedMessage());
        }
    }

    public ServiceResponse getCollectionById(Long collectionId) {
        try {
            Optional<Collection> collection = repository.findById(collectionId);
            collection.orElseThrow(CollectionNotFoundException::new);
            ViewCollectionDto collectionDto = new ViewCollectionDto();

            collectionDto.setName(collection.get().getName());
            collectionDto.setDescription(collection.get().getDescription());
            collectionDto.setCollectionId(collection.get().getId());
            collectionDto.setConnection(collection.get().getConnection());
            collectionDto.setDatabase(collection.get().getConnection().getDatabase());

            return success(true).code(200).result(collectionDto);
        } catch (Exception e) {
            return success(false).code(500).errors(e.getLocalizedMessage());
        }
    }

    public ServiceResponse updateCollection(CollectionInitializationDto init, Long collectionId) {

        try {
            Optional<Database> database = databaseRepository.findById(init.getDatabaseId());
            database.orElseThrow(RuntimeException::new);

            // collection data
            Optional<Collection> collection = collectionRepository.findById(collectionId);
            collection.orElseThrow(CollectionNotFoundException::new);

            Collection collectionData = collection.get();
            collectionData.setName(init.getCollectionName());
            collectionData.setDescription(init.getCollectionDesc());
            collectionData.setUrl(init.getCollectionName().toLowerCase().replaceAll("\\s", "-"));

            // connection data
            Optional<Connection> connection = connectionRepository.findById(collection.get().getConnection().getId());
            connection.orElseThrow(ConnectException::new);

            Connection connectionData = connection.get();
            connectionData.setDatabase(database.get());
            connectionData.setName(init.getConnectionName());
            connectionData.setDescription(init.getConnectionDesc());
            connectionData.setDatabaseName(init.getConnectionDbName());
            connectionData.setHost(init.getConnectionHost());
            connectionData.setPort(init.getConnectionPort());
            connectionData.setUsername(init.getConnectionUsername());
            connectionData.setPassword(init.getConnectionPassword());

            collectionData.setConnection(connectionData);

            connectionRepository.save(connectionData);
            collectionRepository.save(collectionData);

            return success(true).code(200).message("Congratulation, your collection successfully updated!").result(collection);
        } catch (DataIntegrityViolationException e) {
            return success(false).code(500).errors(e.getCause().getMessage());
        } catch (Exception e) {
            return success(false).code(500).errors(e.getCause().getMessage());
        }
    }
}