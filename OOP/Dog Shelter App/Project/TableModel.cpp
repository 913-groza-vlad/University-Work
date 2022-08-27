#include "TableModel.h"

TableModel::TableModel(Repository* adoptionList) : adoptionList{ adoptionList }
{
}

int TableModel::rowCount(const QModelIndex& parent) const
{
	return this->adoptionList->getSize();
}

int TableModel::columnCount(const QModelIndex& parent) const
{
	return 5;
}

QVariant TableModel::data(const QModelIndex& index, int role) const
{
	int row = index.row();
	int col = index.column();
	Dog dog = this->adoptionList->getList()[row];

	if (role == Qt::DisplayRole)
	{
		switch (col)
		{
		case 0:
			return QString::number(dog.get_dogId());
		case 1:
			return QString::fromStdString(dog.get_breed());
		case 2:
			return QString::fromStdString(dog.get_name());
		case 3:
			return QString::number(dog.get_age());
		case 4:
			return QString::fromStdString(dog.get_photo());
		default:
			break;
		}
	}
	if (role == Qt::BackgroundRole)
	{
		if (row % 2 == 1) {
			QBrush brush{ QColor{ 153, 220, 43 } };
			return brush;
		}
		else
		{
			QBrush brush{ QColor{ 120, 110, 200 } };
			return brush;
		}
	}
	if (role == Qt::UserRole)
	{
		if (col == 3)
			return dog.get_age();
		if (col == 0)
			return dog.get_dogId();
	}

	return QVariant();
}

QVariant TableModel::headerData(int section, Qt::Orientation orientation, int role) const
{
	if (orientation == Qt::Horizontal)
		if (role == Qt::DisplayRole)
		{
			switch (section)
			{
			case 0:
				return QString{ "ID" };
			case 1:
				return QString{ "Breed" };
			case 2:
				return QString{ "Name" };
			case 3:
				return QString{ "Age" };
			case 4:
				return QString{ "Link" };
			default:
				break;
			}
		}
	return QVariant();
}

bool TableModel::insertRows(int position, int rows, const QModelIndex& parent)
{
	int n = this->adoptionList->getSize();
	this->beginInsertRows(QModelIndex{}, n, n+1);
	this->insertRow(this->rowCount());
	this->endInsertRows();
	return true;
}
