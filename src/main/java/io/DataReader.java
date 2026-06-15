package io;

import data.Dataset;

public interface DataReader
{
    Dataset read(String filePath, int skipLines);
}
