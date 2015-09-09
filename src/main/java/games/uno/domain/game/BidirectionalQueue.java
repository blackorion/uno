package games.uno.domain.game;

import java.util.*;

public class BidirectionalQueue<T> extends AbstractQueue<T> implements Queue<T>
{
    private final BidirectionalIterator ITERATOR = new BidirectionalIterator();
    private List<T> elements = new ArrayList<>();
    private Direction direction = Direction.CLOCKWISE;
    private int index = 0;

    public void setElements(List<T> elements) { this.elements = elements; }

    public List<T> getElements() { return elements; }

    public void changeDirection() { direction = direction.change(); }

    public Direction getDirection() { return direction; }

    public int size() { return elements.size(); }

    @Override
    public boolean offer(T element) {
        return elements.add(element);
    }

    @Override
    public T poll() {
        if ( size() > 0 )
            return elements.remove(index);
        else
            return null;
    }

    @Override
    public T peek() {
        if ( size() > 0 )
            return elements.get(index);
        else
            return null;
    }

    public T peekNext() {
        return ITERATOR.peekNext();
    }

    public void next() {
        if ( ITERATOR.hasNext() )
            ITERATOR.next();
    }

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }

    public enum Direction
    {
        CLOCKWISE {
            @Override
            int nextTurnIndex(int index, int size) {
                if ( ++index == size )
                    return 0;
                else
                    return index;
            }

            @Override
            Direction getContradiction() {
                return COUNTERCLOCKWISE;
            }
        },

        COUNTERCLOCKWISE {
            @Override
            int nextTurnIndex(int index, int size) {
                if ( index == 0 )
                    return --size;
                else
                    return --index;
            }

            @Override
            Direction getContradiction() {
                return CLOCKWISE;
            }
        };

        abstract int nextTurnIndex(int index, int size);

        abstract Direction getContradiction();

        public Direction change() {
            return getContradiction();
        }
    }

    private class BidirectionalIterator implements Iterator<T>
    {
        @Override
        public boolean hasNext() {
            return elements.size() > 0;
        }

        @Override
        public T next() {
            index = nextTurnIndex();

            return elements.get(index);
        }

        public T peekNext() {
            return elements.get(nextTurnIndex());
        }

        private int nextTurnIndex() {
            return direction.nextTurnIndex(index, elements.size());
        }

        private void changeDirection() {
            direction = direction.change();
        }
    }
}
