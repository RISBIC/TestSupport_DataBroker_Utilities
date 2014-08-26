/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.dbutilities.testsupport.dataflownodes.dummy;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.risbic.intraconnect.basic.BasicDataConsumer;
import org.risbic.intraconnect.basic.BasicDataProvider;
import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataProvider;
import com.arjuna.databroker.data.DataSink;

public class DummyDataSink implements DataSink
{
    private static final Logger logger = Logger.getLogger(DummyDataSink.class.getName());

    public DummyDataSink(String name, Map<String, String> properties)
    {
        logger.log(Level.FINE, "DummyDataSink: " + name + ", " + properties);

        _name       = name;
        _properties = properties;

        _dataConsumer = new BasicDataConsumer<String>(this, "sendData", String.class);
        _dataProvider = new BasicDataProvider<String>(this);
        
        _receivedData = new LinkedList<String>();
    }

    @Override
    public String getName()
    {
        logger.log(Level.FINE, "DummyDataSink.getName");

        return _name;
    }

    @Override
    public Map<String, String> getProperties()
    {
        logger.log(Level.FINE, "DummyDataSink.getProperties");

        return Collections.unmodifiableMap(_properties);
    }

    @Override
    public Collection<Class<?>> getDataConsumerDataClasses()
    {
        logger.log(Level.FINE, "DummyDataSink.getDataConsumerDataClasses");

        Set<Class<?>> dataConsumerDataClasses = new HashSet<Class<?>>();

        dataConsumerDataClasses.add(String.class);

        return dataConsumerDataClasses;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> DataConsumer<T> getDataConsumer(Class<T> dataClass)
    {
        logger.log(Level.FINE, "DummyDataSink.getDataConsumer");

        if (dataClass == String.class)
            return (DataConsumer<T>) _dataConsumer;
        else
            return null;
    }

    public void sendData(String data)
    {
        logger.log(Level.FINE, "DummyDataSink.sendData");

        _receivedData.add(data);

        _dataProvider.produce(data);
    }

    public List<String> receivedData()
    {
        logger.log(Level.FINE, "DummyDataSink.receivedData");

    	return _receivedData;
    }

    private String               _name;
    private Map<String, String>  _properties;
    private DataConsumer<String> _dataConsumer;
    private DataProvider<String> _dataProvider;

    private List<String> _receivedData;
}