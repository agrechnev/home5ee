package agrechnev.models;

import agrechnev.helpers.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Oleksiy Grechnyev on 10/29/2016.
 * Interface with a method String toShortString()
 * Also contains a static method to print collections via toShortString (after sorting)
 * Extends Comparable to allow for sorting
 */
public interface ShortPrintable<T> extends Comparable<T> {
    /**
     * Print a short summary of an entity, no recursion to other entities
     * Only things line primitives, strings, dates might be printed
     * The idea here is to avoid infinite recursion with one-to-many links
     * which easily happens if using toString()
     *
     * @return Comma-separated sorted string
     */
    String toShortString();


    static String printEntity(ShortPrintable<?> entity) {
        return (entity != null) ? entity.toShortString() : "NULL";
    }

    static String printCollection(Collection<? extends ShortPrintable<?>> collection) {

        if (collection == null || collection.size() == 0) {
            return "[]"; // Nothing to do
        }

        // Copy collection as list and sort it
        List<ShortPrintable<?>> list = new ArrayList<>(collection);
        list.sort(null); // Using the build-in comparator of Comparable

        // Convert to a list of Strings via toShortString()
        List<String> stringList = list.stream().map(ShortPrintable::printEntity).collect(Collectors.toList());

        // Return a comma-separated String
        return "[".concat(Util.separatedList(stringList, ", ")).concat("]");
    }
}
