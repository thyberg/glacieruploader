package de.kopis.glacier.commands;

/*
 * #%L
 * glacieruploader-command
 * %%
 * Copyright (C) 2012 - 2016 Carsten Ringe
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.glacier.AmazonGlacier;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sqs.AmazonSQS;
import de.kopis.glacier.parsers.GlacierUploaderOptionParser;
import joptsimple.OptionSet;

public abstract class AbstractCommand {
    protected final Logger log;

    protected static final int OK_RETURN_CODE = 0;
    protected static final int AMAZON_SERVICE_EXCEPTION_RETURN_CODE = 10;
    protected static final int AMAZON_CLIENT_EXCEPTION_RETURN_CODE  = 11;
    protected static final int IO_ERROR_RETURN_CODE = 12;
    protected static final int FILE_NOT_FOUND_RETURN_CODE = 13;
    protected static final int NO_SUCH_ALGORITHM_RETURN_CODE = 14;

    protected AWSCredentials credentials = null;
    protected AmazonGlacier client = null;
    protected AmazonSQS sqs = null;
    protected AmazonSNS sns = null;

    public AbstractCommand(AmazonGlacier client, AmazonSQS sqs, AmazonSNS sns) {
        Validate.notNull(client);
        Validate.notNull(sqs);
        Validate.notNull(sns);

        this.client = client;
        this.sqs = sqs;
        this.sns = sns;

        this.log = LoggerFactory.getLogger(this.getClass());
    }

    public abstract int exec(OptionSet options, GlacierUploaderOptionParser optionParser);

    public abstract boolean valid(OptionSet options, GlacierUploaderOptionParser optionParser);

}