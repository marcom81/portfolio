package name.abuchen.portfolio.model;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import name.abuchen.portfolio.Messages;

public class IndustryClassification
{
    public static class Category
    {
        private String id;
        private String label;
        private String description;

        private Category parent;
        private List<Category> children = new ArrayList<Category>();

        private Category(String id, Category parent)
        {
            this.id = id;
            this.parent = parent;
        }

        public String getId()
        {
            return id;
        }

        public String getLabel()
        {
            return label;
        }

        public String getDescription()
        {
            return description;
        }

        public Category getParent()
        {
            return parent;
        }

        public List<Category> getChildren()
        {
            return children;
        }
    }

    private static Category ROOT_CATEGORY;

    static
    {
        ResourceBundle bundle = ResourceBundle.getBundle("name.abuchen.portfolio.model.industry-classification"); //$NON-NLS-1$

        ROOT_CATEGORY = new Category("0", null); //$NON-NLS-1$
        ROOT_CATEGORY.label = Messages.LabelIndustryClassification;

        readCategory(bundle, ROOT_CATEGORY);
    }

    private static void readCategory(ResourceBundle bundle, Category parent)
    {
        String children = getString(bundle, parent.id + ".children"); //$NON-NLS-1$
        if (children == null)
            return;

        for (String childId : children.split(",")) //$NON-NLS-1$
        {
            Category child = new Category(childId, parent);
            child.label = getString(bundle, childId + ".label"); //$NON-NLS-1$
            if (child.label == null)
                continue;

            child.description = getString(bundle, childId + ".description"); //$NON-NLS-1$

            parent.children.add(child);
            child.parent = parent;

            readCategory(bundle, child);
        }
    }

    private static String getString(ResourceBundle bundle, String key)
    {
        try
        {
            return bundle.getString(key);
        }
        catch (MissingResourceException e)
        {
            return null;
        }
    }

    public Category getRootCategory()
    {
        return ROOT_CATEGORY;
    }

}
