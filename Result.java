import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Algorithms used by the inventory batch-code challenge. */
class Result {
    private static final class OrGroup {
        private final int value;
        private long count;

        private OrGroup(int value, long count) {
            this.value = value;
            this.count = count;
        }
    }

    /**
     * Counts contiguous subarrays whose bitwise OR occurs anywhere in batchCodes.
     *
     * @param batchCodes the inventory batch codes
     * @return the number of compromised subarrays
     */
    public static long countCompromisedBatches(List<Integer> batchCodes) {
        Set<Integer> knownCodes = new HashSet<>(batchCodes);
        List<OrGroup> previous = new ArrayList<>();
        long compromised = 0L;

        for (int code : batchCodes) {
            List<OrGroup> current = new ArrayList<>();
            appendOrMerge(current, code, 1L);

            for (OrGroup group : previous) {
                appendOrMerge(current, group.value | code, group.count);
            }

            for (OrGroup group : current) {
                if (knownCodes.contains(group.value)) {
                    compromised += group.count;
                }
            }
            previous = current;
        }

        return compromised;
    }

    private static void appendOrMerge(List<OrGroup> groups, int value, long count) {
        if (!groups.isEmpty() && groups.get(groups.size() - 1).value == value) {
            groups.get(groups.size() - 1).count += count;
        } else {
            groups.add(new OrGroup(value, count));
        }
    }
}
