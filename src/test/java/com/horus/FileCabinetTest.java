package com.horus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FileCabinetTest {

    private FileCabinet fileCabinet;

    @BeforeEach
    void setUp() {
        // Test data structure:
        // - folder1 (SMALL)
        // - multiFolder1 (LARGE)
        //     - folder2 (SMALL)
        //     - multiFolder2 (MEDIUM)
        //         - folder3 (SMALL)

        Folder folder1 = new FolderImpl("folder1", "SMALL");
        Folder folder2 = new FolderImpl("folder2", "SMALL");
        Folder folder3 = new FolderImpl("folder3", "SMALL");

        MultiFolder multiFolder2 = new MultiFolderImpl("multiFolder2", "MEDIUM", Collections.singletonList(folder3));
        MultiFolder multiFolder1 = new MultiFolderImpl("multiFolder1", "LARGE", Arrays.asList(folder2, multiFolder2));

        List<Folder> rootFolders = Arrays.asList(folder1, multiFolder1);

        fileCabinet = new FileCabinet(rootFolders);
    }

    @Test
    void findFolderByName_shouldReturnFolder_whenExistsAtTopLevel() {
        Optional<Folder> result = fileCabinet.findFolderByName("folder1");

        assertTrue(result.isPresent());
        assertEquals("folder1", result.get().getName());
    }

    @Test
    void findFolderByName_shouldReturnFolder_whenExistsDeepInStructure() {
        Optional<Folder> result = fileCabinet.findFolderByName("folder3");

        assertTrue(result.isPresent());
        assertEquals("folder3", result.get().getName());
    }

    @Test
    void findFolderByName_shouldReturnMultiFolder_whenNameMatches() {
        Optional<Folder> result = fileCabinet.findFolderByName("multiFolder2");

        assertTrue(result.isPresent());
        assertEquals("multiFolder2", result.get().getName());
    }

    @Test
    void findFolderByName_shouldReturnEmpty_whenFolderDoesNotExist() {
        Optional<Folder> result = fileCabinet.findFolderByName("nonExistingFolder");

        assertFalse(result.isPresent());
    }

    @Test
    void findFoldersBySize_shouldReturnAllFoldersOfGivenSize() {
        List<Folder> result = fileCabinet.findFoldersBySize("SMALL");

        // Expected: folder1, folder2, folder3
        assertEquals(3, result.size());
    }

    @Test
    void findFoldersBySize_shouldReturnEmptyList_whenSizeDoesNotMatch() {
        List<Folder> result = fileCabinet.findFoldersBySize("EXTRA_LARGE");

        assertTrue(result.isEmpty());
    }

    @Test
    void count_shouldCountAllFoldersAndMultiFolders() {
        int count = fileCabinet.count();

        // Expected: 3 folders + 2 multiFolders
        assertEquals(5, count);
    }

    @Test
    void count_shouldReturnZero_whenCabinetIsEmpty() {
        FileCabinet emptyCabinet = new FileCabinet(Collections.emptyList());
        assertEquals(0, emptyCabinet.count());
    }

    // --- Helper classes for testing (Mock implementations) ---

    private static class FolderImpl implements Folder {
        private final String name;
        private final String size;

        public FolderImpl(String name, String size) {
            this.name = name;
            this.size = size;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getSize() {
            return size;
        }
    }

    private static class MultiFolderImpl implements MultiFolder {
        private final String name;
        private final String size;
        private final List<Folder> folders;

        public MultiFolderImpl(String name, String size, List<Folder> folders) {
            this.name = name;
            this.size = size;
            this.folders = folders;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getSize() {
            return size;
        }

        @Override
        public List<Folder> getFolders() {
            return folders;
        }
    }
}