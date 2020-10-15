package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateSlotException;
import seedu.address.model.person.exceptions.SlotNotFoundException;
import seedu.address.model.person.exceptions.SlotOverlapDurationException;

/**
 * A list of slots that enforces uniqueness between its elements and does not allow nulls.
 * A slot is considered unique by comparing using {@code Slot#isSameSlot(Slot)}.
 * As such, adding and updating of
 * slots uses Slot#isSameSlot(Slot) for equality
 * so as to ensure that the slot being added or updated is unique in terms of identity in the UniqueSlotList.
 * However, the removal of a slot uses Slot#equals(Object) so
 * as to ensure that the slot with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Slot#isSameSlot(Slot)
 */
public class UniqueSlotList implements Iterable<Slot> {
    private final ObservableList<Slot> internalList = FXCollections.observableArrayList();
    private final ObservableList<Slot> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Slot as the given argument.
     */
    public boolean contains(Slot toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameSlot);
    }

    /**
     * Returns true if the list contains a Slot with overlapping duration as the given argument.
     * @param toCheck The slot to check.
     * @return True if the list contains a Slot with overlapping duration as the Slot to check.
     */
    public boolean hasOverlapDuration(Slot toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::hasOverlapDuration);
    }

    /**
     * Adds a slot to the list.
     * The Slot must not already exist in the list.
     */
    public void add(Slot toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateSlotException();
        }
        if (hasOverlapDuration(toAdd)) {
            throw new SlotOverlapDurationException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent slot from the list.
     * The slot must exist in the list.
     */
    public void remove(Slot toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new SlotNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Slot> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Slot> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueSlotList // instanceof handles nulls
                && internalList.equals(((UniqueSlotList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}