import numpy as np
import pandas as pd
%matplotlib inline
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.feature_selection import SelectKBest
from sklearn.feature_selection import chi2
import Orange

meta = pd.read_csv('data/building_metadata.csv')
df_train = pd.read_csv('data/train.csv', parse_dates=['timestamp'])
df_test = pd.read_csv('data/test.csv')
weather_train = pd.read_csv('data/weather_train.csv', parse_dates=['timestamp'])
weather_test = pd.read_csv('data/weather_test.csv')

def reduce_mem_usage(df, verbose=True):
    numerics = ['int16', 'int32', 'int64', 'float16', 'float32', 'float64']
    start_mem = df.memory_usage().sum() / 1024**2    
    for col in df.columns:
        col_type = df[col].dtypes
        if col_type in numerics:
            c_min = df[col].min()
            c_max = df[col].max()
            if str(col_type)[:3] == 'int':
                if c_min > np.iinfo(np.int8).min and c_max < np.iinfo(np.int8).max:
                    df[col] = df[col].astype(np.int8)
                elif c_min > np.iinfo(np.int16).min and c_max < np.iinfo(np.int16).max:
                    df[col] = df[col].astype(np.int16)
                elif c_min > np.iinfo(np.int32).min and c_max < np.iinfo(np.int32).max:
                    df[col] = df[col].astype(np.int32)
                elif c_min > np.iinfo(np.int64).min and c_max < np.iinfo(np.int64).max:
                    df[col] = df[col].astype(np.int64)  
            else:
                if c_min > np.finfo(np.float16).min and c_max < np.finfo(np.float16).max:
                    df[col] = df[col].astype(np.float16)
                elif c_min > np.finfo(np.float32).min and c_max < np.finfo(np.float32).max:
                    df[col] = df[col].astype(np.float32)
                else:
                    df[col] = df[col].astype(np.float64)    
    end_mem = df.memory_usage().sum() / 1024**2
    if verbose: print('Mem. usage decreased to {:5.2f} Mb ({:.1f}% reduction)'.format(end_mem, 100 * (start_mem - end_mem) / start_mem))
    return df


'''Use numbers to represent each category of primary_use'''
primary_use_conversion = {}
for i, pu in enumerate(meta.primary_use.value_counts().index):
    primary_use_conversion[pu] = i
print(primary_use_conversion)

'''Changing elements in _metadata's primary_use column to corresponding numbers'''
meta['primary_use'] = meta.primary_use.apply(lambda pu: primary_use_conversion[pu])
print(meta)

meta = \
meta.fillna({"year_built": 2050,
                  "floor_count": 0})\
         .astype({"site_id": np.int8, 
                  "building_id": np.int16,
                  "primary_use": np.int8,
                  "year_built": np.int16,
                  "floor_count": np.int8})
print(meta)

'''Modify the "wind_direction" column by converting directions to numbers 0-8 and convert "timestamp" column into date and time'''

weather_test['wind_direction'] = weather_test['wind_direction'].apply(Orange.wind_direction_conversion)
weather_test['timestamp'] = pd.to_datetime(weather_test['timestamp'])
print(weather_test)


'''Fill some NAs of _wtest and _wtrain and change data types'''
weather_test = \
weather_test.fillna({"cloud_coverage": -1,
              }) \
      .astype({"air_temperature": np.float16, 
               "cloud_coverage": np.int8,
               "dew_temperature": np.float16,
               "precip_depth_1_hr": np.float16, # np.int16 after fillna
               "sea_level_pressure": np.float16,
               "wind_direction": np.float16, # np.int8 after fillna
               "wind_speed": np.float16})
print(weather_test)

'''Changing the _wtrain just as before to _wtest'''
weather_train['wind_direction'] = weather_train['wind_direction'].apply(Orange.wind_direction_conversion)
weather_train['timestamp'] = pd.to_datetime(weather_train['timestamp'])
print(weather_train)

weather_train = \
weather_train.fillna({"cloud_coverage": -1,
              }) \
       .astype({"air_temperature": np.float16, 
                "cloud_coverage": np.int8,
                "dew_temperature": np.float16,
                "precip_depth_1_hr": np.float16, # np.int16 after fillna
                "sea_level_pressure": np.float16,
                "wind_direction": np.float16, # np.int8 after fillna
                "wind_speed": np.float16})
print(weather_train)


'''Change "timestamp" column to data time and convert data types
'''

df_test['timestamp'] = pd.to_datetime(df_test['timestamp'])
df_test = df_test.astype({"building_id": np.int16,
                      "meter": np.int8})
print(df_test) 

df_train['timestamp'] = pd.to_datetime(df_test['timestamp'])
df_train = df_train.astype({"building_id": np.int16,
                        "meter": np.int8})
print(df_train)










# Reducing memory
df_train = reduce_mem_usage(df_train)
df_test = reduce_mem_usage(df_test)

weather_train = reduce_mem_usage(weather_train)
weather_test = reduce_mem_usage(weather_test)
meta = reduce_mem_usage(meta)

display(df_train.shape)
df_train.head()

display(meta.shape)
meta.head()

display(weather_train.shape)
weather_train.head()

 # merge all three training dataset
train_df = df_train.copy()
train_df = train_df.merge(meta, on="building_id", how="left")
print(train_df['site_id'].dtype)
print(weather_train['site_id'].dtype)
print(train_df['timestamp'].dtype)
print(weather_train['timestamp'].dtype)
train_df = train_df.merge(weather_train, on=['site_id', 'timestamp'], how="left")
train_df.head()
display(train_df.shape)

corr_matrix = train_df.corr()
corr_matrix["meter_reading"].sort_values(ascending=False)




