package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

public class Timetable {

    private final UniqueSlotList slots;

    {
        slots = new UniqueSlotList();
    }

    /**
     * Returns true if this Slot is already in use in the Timetable.
     * @param slot The slot to be checked.
     * @return true if this Slot is already in use in the Timetable.
     */
    public boolean hasSlot(Slot slot) {
        requireNonNull(slot);
        return slots.contains(slot);
    }

    /**
     * Returns true if this slot has overlapping duration with the other slots in timetable.
     * @param slot The slot to be checked.
     * @return true if this slot has overlapping duration with the other slots in timetable.
     */
    public boolean hasOverlapDuration(Slot slot) {
        requireNonNull(slot);
        return slots.hasOverlapDuration(slot);
    }

    public void addSlot(Slot slot) {
        slots.add(slot);
    }

    public void deleteSlot(Slot slot) {
        slots.remove(slot);
    }
}