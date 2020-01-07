import pandas as pd
import os
import tarfile
import urllib
import matplotlib.pyplot as plt
import numpy as np

DOWNLOAD_ROOT = "https://raw.githubusercontent.com/ageron/handson-ml2/master/"
HOUSING_PATH = os.path.join("datasets", "housing")
HOUSING_URL = DOWNLOAD_ROOT + "datasets/housing/housing.tgz"

'''Download the data from the HOUSING_URL:
Creates a datasets/housing directory, download 
the housing.tgz file, and extracts the housing.csv file
from it in this directory 
'''
def fetch_housing_data(housing_url=HOUSING_URL, housing_path=HOUSING_PATH):
    os.makedirs(housing_path, exist_ok=True)
    tgz_path = os.path.join(housing_path, "housing.tgz")
    urllib.request.urlretrieve(housing_url, tgz_path)
    housing_tgz = tarfile.open(tgz_path)
    housing_tgz.extractall(path=housing_path)
    housing_tgz.close()
    
'''This function returns a pandas DataFrame object containing all the data in the csv file
'''
def load_housing_data(housing_path=HOUSING_PATH):
    csv_path = os.path.join(housing_path, "housing.csv")
    return pd.read_csv(csv_path)


fetch_housing_data()
housing = load_housing_data()
'''Pandas head() method is used to return top n (5 by default) rows of a data frame or series.'''

housing.head()
housing.head(6)

'''Pandas info() is used to get a concise summary of the dataframe, including rangeindex(number of entries), number of columns, names of columns, number of items in columns, whether there are null items, data type of entries in a column'''

'''value_counts() is used to count the number of appearances of each type of entry in a Series object, which can be the column of a dataframe. The [] is used to access a column of the dataframe'''

'''describe() is used to show the maximum , minimum, mean, median, standard deviation, count, and percentile of values in each column. If the entries are Strings, different stats were returned like count of values, unique values, top and frequency of occurrence. Note that null values are ignored in count.'''

housing.info()
print(housing["ocean_proximity"].value_counts())
print(housing["longitude"].value_counts())
print(housing.describe())

'''bins represent the number of categories in the histogram and figsize is a tuple describing the sizes of the histograms. figsize represents the size of the histogram in inches.'''
housing.hist(bins=50,figsize=(20,15))
plt.show()

'''Split_train_test(data, test_ratio) returns two random dataframes of training data set and testing data set.
np.random.permutation(a) (a is an int) returns a permutation of np.arange(a). 
np.random.permutation(a) (a is a 1-dimensional array) returns a 1-dimensional array whose elements are 
the permutation of the elements in a.
np.random.permutation(a) (a is multi-dimensional array) returns an array of the same dimension with the elements in the first axis permuted.

pandas.Dataframe.iloc[i] (i is an int) returns the row i of the Dataframe in a column format
pandas.Dataframe.iloc[i] (i is an int 1-dimensional array) returns the rows with the same number as elements in i as a Dataframe
pandas.Dataframe.iloc[i] (i is a slice object, e.g iloc[1:3]) returns the rows within the range of slice(the former roow number inclusive, the latter row number exclusive) as a Dataframe
pandas.Dataframe.iloc[i,j] (i and j are int) returns the entry in row i and column j
pandas.Dataframe.iloc[i,j] (i and j are 1-dimensional lists of int) returns the row numbers as elements in i and column numbers as elements in j combined together as a Dataframe
pandas.Dataframe.iloc[i,j] (i and j are slice objects) returns the rows and columns represented of the slices as a Dataframe

len(Dataframe) returns the number of rows in the Dataframe

np.random.seed(i) (i is an int) Use the int seed in random functions(methods) so that 
the next call to the random function has the output determined by the seed. This 
method can be used before np.random.permutation(a) to ensure the returned permutation is
always the same. In this way, the machine learning algorithms will not be able to see the 
whole data set. However, we assume that the data set will not change.
'''

def split_train_test(data, test_ratio):
    '''np.random.seed(42) can be added here.'''
    shuffled_indices = np.random.permutation(len(data))
    test_set_size = int(len(data) * test_ratio)
    test_indices = shuffled_indices[:test_set_size]
    train_indices = shuffled_indices[test_set_size:]
    return data.iloc[train_indices], data.iloc[test_indices]

train_set, test_set = split_train_test(housing, 0.2)
len(train_set)
len(test_set)


'''split_train_test_by_id(data, test_ratio, id_column) determines '''

def split_train_test_by_id(data, test_ratio, id_column):
    ids = data[id_column]
    in_test_set = ids.apply(lambda id_: test_set_check(id_, test_ratio))
    return data.loc[~in_test_set], data.loc[in_test_set]


from zlib import crc32

'''test_set_check(identifier, test_ratio) singles out the test data set with identifier and test_ratio

CRC32 is an error-detecting function that uses a CRC32 algorithm to detect changes between source and target data. The CRC32 function converts a variable-length string into an 8-character string that is a text representation of the hexadecimal value of a 32 bit-binary sequence.'''

def test_set_check(identifier, test_ratio):
    return crc32(np.int64(identifier)) & 0xffffffff < test_ratio * 2**32

'''pandas.Series.apply(self, func, convert_dtype=True, args=(), **kwds),
Invoke function on values of Series.

Can be ufunc (a NumPy function that applies to the entire Series) or a Python function that only works on single values.

convert_dtype : bool, default True
Try to find better dtype for elementwise function results. If False, leave as dtype=object.

args : tuple
Positional arguments passed to func after the series value.

**kwds
Additional keyword arguments passed to func.

Returns Series or DataFrame
If func returns a Series object the result will be a DataFrame





pandas.DataFrame.loc[]
Access a group of rows and columns by label(s) or a boolean array.

.loc[] is primarily label based, but may also be used with a boolean array.

Allowed inputs are:

A single label, e.g. 5 or 'a', (note that 5 is interpreted as a label of the index, and never as an integer position along the index).

A list or array of labels, e.g. ['a', 'b', 'c'].

A slice object with labels, e.g. 'a':'f'.

Warning Note that contrary to usual python slices, both the start and the stop are included
A boolean array of the same length as the axis being sliced, e.g. [True, False, True].

A callable function with one argument (the calling Series or DataFrame) and that returns valid output for indexing (one of the above)

>>> df = pd.DataFrame([[1, 2], [4, 5], [7, 8]],
...      index=['cobra', 'viper', 'sidewinder'],
...      columns=['max_speed', 'shield'])
>>> df
            max_speed  shield
cobra               1       2
viper               4       5
sidewinder          7       8

Single label. Note this returns the row as a Series.

>>> df.loc['viper']
max_speed    4
shield       5
Name: viper, dtype: int64

List of labels. Note using [[]] returns a DataFrame.

>>> df.loc[['viper', 'sidewinder']]
            max_speed  shield
viper               4       5
sidewinder          7       8

Single label for row and column
>>> df.loc['cobra', 'shield']
2

>>> df.loc['cobra':'viper', 'max_speed']
cobra    1
viper    4
Name: max_speed, dtype: int64

Boolean list with the same length as the row axis

>>> df.loc[[False, False, True]]
            max_speed  shield
sidewinder          7       8

Conditional that returns a boolean Series

>>> df.loc[df['shield'] > 6]
            max_speed  shield
sidewinder          7       8

Conditional that returns a boolean Series with column labels specified

>>> df.loc[df['shield'] > 6, ['max_speed']]
            max_speed
sidewinder          7

Callable that returns a boolean Series

>>> df.loc[lambda df: df['shield'] == 8]
            max_speed  shield
sidewinder          7       8
Setting values

Set value for all items matching the list of labels

>>> df.loc[['viper', 'sidewinder'], ['shield']] = 50
>>> df
            max_speed  shield
cobra               1       2
viper               4      50
sidewinder          7      50

Set value for an entire row

>>> df.loc['cobra'] = 10
>>> df
            max_speed  shield
cobra              10      10
viper               4      50
sidewinder          7      50

Set value for an entire column

>>> df.loc[:, 'max_speed'] = 30
>>> df
            max_speed  shield
cobra              30      10
viper              30      50
sidewinder         30      50

Set value for rows matching callable condition

>>> df.loc[df['shield'] > 35] = 0
>>> df
            max_speed  shield
cobra              30      10
viper               0       0
sidewinder          0       0
Getting values on a DataFrame with an index that has integer labels

Another example using integers for the index

>>> df = pd.DataFrame([[1, 2], [4, 5], [7, 8]],
...      index=[7, 8, 9], columns=['max_speed', 'shield'])
>>> df
   max_speed  shield
7          1       2
8          4       5
9          7       8
Slice with integer labels for rows. As mentioned above, note that both the start and stop of the slice are included.

>>> df.loc[7:9]
   max_speed  shield
7          1       2
8          4       5
9          7       8
Getting values with a MultiIndex

A number of examples using a DataFrame with a MultiIndex

>>> tuples = [
...    ('cobra', 'mark i'), ('cobra', 'mark ii'),
...    ('sidewinder', 'mark i'), ('sidewinder', 'mark ii'),
...    ('viper', 'mark ii'), ('viper', 'mark iii')
... ]
>>> index = pd.MultiIndex.from_tuples(tuples)
>>> values = [[12, 2], [0, 4], [10, 20],
...         [1, 4], [7, 1], [16, 36]]
>>> df = pd.DataFrame(values, columns=['max_speed', 'shield'], index=index)
>>> df
                     max_speed  shield
cobra      mark i           12       2
           mark ii           0       4
sidewinder mark i           10      20
           mark ii           1       4
viper      mark ii           7       1
           mark iii         16      36

Single label. Note this returns a DataFrame with a single index.

>>> df.loc['cobra']
         max_speed  shield
mark i          12       2
mark ii          0       4

Single index tuple. Note this returns a Series.

>>> df.loc[('cobra', 'mark ii')]
max_speed    0
shield       4
Name: (cobra, mark ii), dtype: int64

Single label for row and column. Similar to passing in a tuple, this returns a Series.

>>> df.loc['cobra', 'mark i']
max_speed    12
shield        2
Name: (cobra, mark i), dtype: int64

Single tuple. Note using [[]] returns a DataFrame.

>>> df.loc[[('cobra', 'mark ii')]]
               max_speed  shield
cobra mark ii          0       4

Single tuple for the index with a single label for the column

>>> df.loc[('cobra', 'mark i'), 'shield']
2

Slice from index tuple to single label

>>> df.loc[('cobra', 'mark i'):'viper']
                     max_speed  shield
cobra      mark i           12       2
           mark ii           0       4
sidewinder mark i           10      20
           mark ii           1       4
viper      mark ii           7       1
           mark iii         16      36

Slice from index tuple to index tuple

>>> df.loc[('cobra', 'mark i'):('viper', 'mark ii')]
                    max_speed  shield
cobra      mark i          12       2
           mark ii          0       4
sidewinder mark i          10      20
           mark ii          1       4
viper      mark ii          7       1


~ is a binary operator which reverse the 0 and 1 in the binary expression(for boolean expressions, true to false or false to true)

split_train_test_by_id(data, test_ratio, id_column) returns the data set for training and that for testing
by checking whether the row is in the data set for testing(in_test_set is such a Series of True and False)
'''


def split_train_test_by_id(data, test_ratio, id_column):
    ids = data[id_column]
    in_test_set = ids.apply(lambda id_: test_set_check(id_, test_ratio))
    return data.loc[~in_test_set], data.loc[in_test_set]
    
housing_with_id = housing.reset_index()   # adds an "index" column(0,1,2......) which shows row numbers
train_set, test_set = split_train_test_by_id(housing_with_id, 0.2, "index")

'''If you use the row index as a unique identifier, you need to make sure that new data gets appended to the end of the dataset and that no row ever gets deleted. If this is not possible, then you can try to use the most stable features to build a unique identifier. For example, a district’s latitude and longitude are guaranteed to be stable for a few million years, so you could combine them into an ID like so'''

housing_with_id["id"] = housing["longitude"] * 1000 + housing["latitude"]
train_set, test_set = split_train_test_by_id(housing_with_id, 0.2, "id")


'''“The simplest function is train_test_split(), which does pretty much the same thing as the function split_train_test(), with a couple of additional features. First, there is a random_state parameter that allows you to set the random generator seed. Second, you can pass it multiple datasets with an identical number of rows, and it will split them on the same indices (this is very useful, for example, if you have a separate DataFrame for labels)”
'''
from sklearn.model_selection import train_test_split
train_set, test_set = train_test_split(housing, test_size=0.2, random_state=42)


'''https://pandas.pydata.org/pandas-docs/version/0.23.4/generated/pandas.cut.html'''

housing["income_cat"] = pd.cut(housing["median_income"],
                               bins=[0., 1.5, 3.0, 4.5, 6., np.inf],
                               labels=[1, 2, 3, 4, 5])
print(housing["income_cat"])
print(housing["income_cat"].hist())


from sklearn.model_selection import StratifiedShuffleSplit
'''There n_splits is 1 because there is only one pair of stratified random test and training generated. We can
generate many such pairs of the same data set'''
split = StratifiedShuffleSplit(n_splits=1, test_size=0.2, random_state=42)
for train_index, test_index in split.split(housing, housing["income_cat"]):
    strat_train_set = housing.loc[train_index]
    strat_test_set = housing.loc[test_index]
    print(strat_test_set["income_cat"].value_counts()/len(strat_test_set))
    for set_ in (strat_train_set, strat_test_set):
        set_.drop("income_cat", axis=1, inplace=True)
    print(strat_train_set)
    print(strat_test_set)
    
'''Scatterplots of the training set'''    
housing = strat_train_set.copy()
housing.plot(kind = "scatter", x = "longitude", y = "latitude")
housing.plot(kind = "scatter", x = "longitude", y = "latitude",alpha=0.1)
housing.plot(kind="scatter", x="longitude", y="latitude", alpha=0.4,
    s=housing["population"]/100, label="population", figsize=(10,7),
    c="median_house_value", cmap=plt.get_cmap("jet"), colorbar=True,
)
plt.legend()


'''Look for the standard correlation coefficient between every pair of attributes'''
corr_matrix = housing.corr()
print(corr_matrix)
print(corr_matrix["median_house_value"].sort_values(ascending=False))

'''Another way to check for correlation between attributes is to use the pandas scatter_matrix() function, which plots every numerical attribute against every other numerical attribute.
'''
from pandas.plotting import scatter_matrix

attributes = ["median_house_value", "median_income", "total_rooms",
              "housing_median_age"]
display(scatter_matrix(housing[attributes], figsize=(12, 8)))


housing.plot(kind="scatter", x="median_income", y="median_house_value",
             alpha=0.1)
'''One last thing you may want to do before preparing the data for Machine Learning algorithms is to try out various attribute combinations. For example, the total number of rooms in a district is not very useful if you don’t know how many households there are. What you really want is the number of rooms per household. Similarly, the total number of bedrooms by itself is not very useful: you probably want to compare it to the number of rooms.'''

housing["rooms_per_household"] = housing["total_rooms"]/housing["households"]
housing["bedrooms_per_room"] = housing["total_bedrooms"]/housing["total_rooms"]
housing["population_per_household"]=housing["population"]/housing["households"]

corr_matrix = housing.corr()

corr_matrix["median_house_value"].sort_values(ascending=False)



'''revert to a clean training set (by copying strat_train_set once again). Let’s also separate the predictors and the labels, since we don’t necessarily want to apply the same transformations to the predictors and the target values (note that drop() creates a copy of the data and does not affect strat_train_set)'''

housing = strat_train_set.drop("median_house_value", axis=1)
housing_labels = strat_train_set["median_house_value"].copy()

'''1.Get rid of the corresponding districts.
2.Get rid of the whole attribute.
3.Set the values to some value (zero, the mean, the median, etc.).
'''

housing.dropna(subset=["total_bedrooms"])    # option 1
housing.drop("total_bedrooms", axis=1)       # option 2
median = housing["total_bedrooms"].median()  # option 3
housing["total_bedrooms"].fillna(median, inplace=True) 


'''Scikit-Learn provides a handy class to take care of missing values: SimpleImputer. Here is how to use it. First, you need to create a SimpleImputer instance, specifying that you want to replace each attribute’s missing values with the median of that attribute'''

from sklearn.impute import SimpleImputer

imputer = SimpleImputer(strategy="median")


