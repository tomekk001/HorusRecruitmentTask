package com.horus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileCabinet implements Cabinet {

    private final List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return getFolderStream()
                .filter(folder -> folder.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return getFolderStream()
                .filter(folder -> folder.getSize().equals(size))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return (int) getFolderStream().count();
    }

    /**
     * Flattens the folder structure into a single Stream using recursion.
     */
    private Stream<Folder> getFolderStream() {
        if (folders == null || folders.isEmpty()) {
            return Stream.empty();
        }
        return folders.stream()
                .flatMap(this::flattenRecursive);
    }

    private Stream<Folder> flattenRecursive(Folder folder) {
        Stream<Folder> current = Stream.of(folder);

        if (folder instanceof MultiFolder) {
            List<Folder> innerFolders = ((MultiFolder) folder).getFolders();

            if (innerFolders != null) {
                Stream<Folder> children = innerFolders.stream()
                        .flatMap(this::flattenRecursive);
                return Stream.concat(current, children);
            }
        }
        return current;
    }
}