package org.apache.tools.ant.types.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Resource;
import org.apache.tools.ant.types.ResourceCollection;

public class Union extends BaseResourceCollectionContainer {
    public static Union getInstance(ResourceCollection rc) {
        return rc instanceof Union ? (Union) rc : new Union(rc);
    }

    public Union(Project project) {
        super(project);
    }

    public Union(ResourceCollection rc) {
        this(Project.getProject(rc), rc);
    }

    public Union(Project project, ResourceCollection rc) {
        super(project);
        add(rc);
    }

    public String[] list() {
        if (isReference()) {
            return ((Union) getCheckedRef(Union.class, getDataTypeName())).list();
        }
        Collection<String> result = getAllToStrings();
        return (String[]) result.toArray(new String[result.size()]);
    }

    public Resource[] listResources() {
        if (isReference()) {
            return ((Union) getCheckedRef(Union.class, getDataTypeName())).listResources();
        }
        Collection<Resource> result = getAllResources();
        return (Resource[]) result.toArray(new Resource[result.size()]);
    }

    protected Collection<Resource> getCollection() {
        return getAllResources();
    }

    @Deprecated
    protected <T> Collection<T> getCollection(boolean asString) {
        return asString ? getAllToStrings() : getAllResources();
    }

    protected Collection<String> getAllToStrings() {
        Set<Resource> allResources = getAllResources();
        ArrayList<String> result = new ArrayList(allResources.size());
        for (Resource r : allResources) {
            result.add(r.toString());
        }
        return result;
    }

    protected Set<Resource> getAllResources() {
        List<ResourceCollection> resourceCollections = getResourceCollections();
        if (resourceCollections.isEmpty()) {
            return Collections.emptySet();
        }
        Set<Resource> result = new LinkedHashSet(resourceCollections.size() * 2);
        for (ResourceCollection<Resource> resourceCollection : resourceCollections) {
            for (Resource r : resourceCollection) {
                result.add(r);
            }
        }
        return result;
    }
}
