package io;

import core.Dataset;

public interface DataReader
{
    Dataset read(String filePath, int skipLines);
}
