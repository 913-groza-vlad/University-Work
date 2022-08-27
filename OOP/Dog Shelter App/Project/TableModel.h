#pragma once
#include <QAbstractTableModel>
#include "Services.h"
#include <QBrush>
#include <QColor>

class TableModel : public QAbstractTableModel
{
private:
	Repository* adoptionList;
public:
	TableModel(Repository* adoptionList);

	int rowCount(const QModelIndex& parent = QModelIndex()) const override;
	int columnCount(const QModelIndex& parent = QModelIndex()) const override;
	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override;

	QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override;

	bool insertRows(int position, int rows, const QModelIndex& parent = QModelIndex()) override;
};

