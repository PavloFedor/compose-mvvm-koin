package com.example.utils.mapper

interface Mapper<AdditionalParams, Source, Destination> : (AdditionalParams, Source) -> Destination