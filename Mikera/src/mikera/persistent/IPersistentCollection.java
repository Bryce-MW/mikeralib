package mikera.persistent;

import java.io.Serializable;
import java.util.Collection;

public interface IPersistentCollection<T> extends Collection<T>, Cloneable, Serializable {

	public PersistentCollection<T> include(final T value);
	
	public PersistentCollection<T> include(final Collection<T> values);

	
	public PersistentCollection<T> deleteAll(final T value);
	
	public PersistentCollection<T> deleteAll(final Collection<T> values);

	public PersistentCollection<T> deleteAll(final PersistentCollection<T> values);

	// query methods
	
	public boolean contains(Object o);

	public boolean containsAll(Collection<?> c);
}
