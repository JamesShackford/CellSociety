package rule;

import java.lang.reflect.InvocationTargetException;

public abstract class Property<T>
{
	private T value;
	private Class<?> valueType;
	private String name;

	public Property(Class<T> valueType, String name)
	{
		this.valueType = valueType;
	}

	public T getValue()
	{
		return value;
	}

	public String getName()
	{
		return name;
	}

	public Class<?> getValueType()
	{
		return valueType;
	}

	public void setValue(T value)
	{
		this.value = value;
	}

	public void setValue(String stringValue)
	{
		try {
			value.getClass().getMethod("getValue", String.class).invoke(value, stringValue);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}