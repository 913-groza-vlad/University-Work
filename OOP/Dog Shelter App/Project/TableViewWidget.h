#pragma once

#include <QWidget>
#include "ui_TableViewWidget.h"
#include "TableModel.h"
#include <QSortFilterProxyModel>
#include <QTableView>
#include <QVBoxLayout>

class TableViewWidget : public QWidget
{
	Q_OBJECT

public:
	TableViewWidget(TableModel* model, QWidget *parent = Q_NULLPTR);
	~TableViewWidget();

private:
	Ui::TableViewWidget ui;
	QTableView* dogsTableView;
};

