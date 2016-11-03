package agrechnev.helpers;

import java.util.Collection;

/**
 * Created by Oleksiy Grechnyev on 10/29/2016.
 * Misc small utils
 */
public class Util {
    // No instantiation, please
    private Util() {
    }

    /**
     * Create a separated list (e.g. comma-separated) out of a collection
     * and return it as a String
     * @param collection  The input collection
     * @param separator   The separator, e.g. ", "
     * @param <E>
     * @return            The list as a string
     */
    public static <E> String separatedList(Collection<E> collection, String separator){
        StringBuilder result=new StringBuilder();

        boolean first=true;

        for (E element : collection) {
            if (!first) {
                result.append(separator); // Add separator before if not first iteration
            } else {
                first=false;
            }

            result.append(element.toString()); // Add next element
        }

        return result.toString();
    }
}
