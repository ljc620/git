package com.zhming.support.db.impl;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.Getter;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.zhming.support.util.RowMapUtil;
import com.zhming.support.util.StringUtil;


/**
 * 
 * @author ly
 *
 */
public class MyBeanResultTransformer extends AliasToBeanResultTransformer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(MyBeanResultTransformer.class);
	private final Class<?> resultClass;
	private boolean isInitialized;
	private String aliases[];
	private Setter setters[];
	private Getter getters[];

	public <T> MyBeanResultTransformer(Class<T> resultClass) {
		super(resultClass);
		if (resultClass == null) {
			throw new IllegalArgumentException("resultClass cannot be null");
		} else {
			isInitialized = false;
			this.resultClass = resultClass;
			return;
		}
	}

	public boolean isTransformedValueATupleElement(String aliases[], int tupleLength) {
		return false;
	}

	public Object transformTuple(Object tuple[], String aliases[]) {
		Object result;
		try {
			if (!isInitialized)
				initialize(aliases);
			else
				check(aliases);
			result = resultClass.newInstance();
			for (int i = 0; i < aliases.length; i++)
				if (setters[i] != null)
					setters[i].set(result, RowMapUtil.transValue(tuple[i],getters[i].getMethod().getReturnType().getName()), null);

		} catch (InstantiationException e) {
			throw new HibernateException((new StringBuilder()).append("Could not instantiate resultclass: ")
					.append(resultClass.getName()).toString());
		} catch (IllegalAccessException e) {
			throw new HibernateException((new StringBuilder()).append("Could not instantiate resultclass: ")
					.append(resultClass.getName()).toString());
		}
		return result;
	}

	private void initialize(String aliases[]) {
		PropertyAccessor propertyAccessor = new ChainedPropertyAccessor(
				new PropertyAccessor[] { PropertyAccessorFactory.getPropertyAccessor(resultClass, null),
						PropertyAccessorFactory.getPropertyAccessor("field") });
		this.aliases = new String[aliases.length];
		setters = new Setter[aliases.length];
		getters=new Getter[aliases.length];
		for (int i = 0; i < aliases.length; i++) {
			String alias = aliases[i];
			if (alias != null) {
				this.aliases[i] = alias;
				alias = StringUtil.underlineToCamel(alias);
				try {
					setters[i] = propertyAccessor.getSetter(resultClass, alias);
					getters[i]=propertyAccessor.getGetter(resultClass, alias);
				} catch (Exception e) {
					logger.warn(resultClass + " property " + alias + " not found");
				}

			}
		}

		isInitialized = true;
	}

	private void check(String aliases[]) {
		if (!Arrays.equals(aliases, this.aliases))
			throw new IllegalStateException((new StringBuilder())
					.append("aliases are different from what is cached; aliases=").append(Arrays.asList(aliases))
					.append(" cached=").append(Arrays.asList(this.aliases)).toString());
		else
			return;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MyBeanResultTransformer that = (MyBeanResultTransformer) o;
		if (!resultClass.equals(that.resultClass))
			return false;
		return Arrays.equals(aliases, that.aliases);
	}

	public int hashCode() {
		int result = resultClass.hashCode();
		result = 31 * result + (aliases == null ? 0 : Arrays.hashCode(aliases));
		return result;
	}

}